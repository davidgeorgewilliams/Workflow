package com.davidgeorgewilliams.workflows.workers;

import com.davidgeorgewilliams.workflows.threads.ThreadLocalTime;
import com.davidgeorgewilliams.workflows.threads.ThreadPool;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Accessors(fluent = true, chain = true)
@Builder(access = AccessLevel.PRIVATE)
@Value
public class WorkerPool {
    Set<Worker<?>> workers;
    String id;
    Supplier<WorkerPool> next;
    ThreadPool threadPool;

    public static WorkerPool of(@NonNull final Set<Worker<?>> workers) {
        return WorkerPool.of(workers, ThreadPool.of(Runtime.getRuntime().availableProcessors()));
    }

    public static WorkerPool of(@NonNull final Set<Worker<?>> workers,
                                @NonNull final ThreadPool threadPool) {
        return WorkerPool.of(UUID.randomUUID().toString(), workers, () -> null, threadPool);
    }

    public static WorkerPool of(@NonNull final String id,
                                @NonNull final Set<Worker<?>> workers,
                                @NonNull final ThreadPool threadPool) {
        return WorkerPool.of(id, workers, () -> null, threadPool);
    }

    public static WorkerPool of(@NonNull final String id,
                                @NonNull final Set<Worker<?>> workers,
                                @NonNull final Supplier<WorkerPool> next,
                                @NonNull final ThreadPool threadPool) {
        return WorkerPool.builder()
                .id(id)
                .threadPool(threadPool)
                .workers(workers)
                .next(next)
                .build();
    }

    public void process() {
        final Deque<Worker<?>> deque = new ArrayDeque<>(workers());
        final List<CompletableFuture<?>> completableFutures = new ArrayList<>();
        while (!deque.isEmpty()) {
            final Worker<?> worker = deque.poll();
            if (worker.isReady()) {
                if (worker.parallel()) {
                    worker.submitted(ThreadLocalTime.of());
                    final CompletableFuture<?> completableFuture = threadPool().submit(worker::process);
                    completableFuture.thenAccept(interrupted -> worker.completed(ThreadLocalTime.of()));
                    completableFutures.add(completableFuture);
                } else {
                    worker.process();
                }
            } else {
                deque.add(worker);
            }
        }
        final CompletableFuture<?>[] futureArray = completableFutures.toArray(new CompletableFuture[0]);
        if (futureArray.length > 0) {
            CompletableFuture.allOf(futureArray).join();
        }
    }
}
