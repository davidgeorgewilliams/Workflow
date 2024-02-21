package davidgeorgewilliams;

import davidgeorgewilliams.threads.ThreadLocalTime;
import davidgeorgewilliams.threads.ThreadPool;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter(value = AccessLevel.PUBLIC)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkerPool {
    ThreadPool threadPool;
    Set<Worker<?>> workers;
    String id;
    Supplier<WorkerPool> next;

    public static WorkerPool of(@NonNull final Set<Worker<?>> workers,
                                @NonNull final ThreadPool cynthiaThreadPool) {
        return new WorkerPool(cynthiaThreadPool, workers, UUID.randomUUID().toString(), () -> null);
    }

    public static WorkerPool of(@NonNull final String id,
                                @NonNull final Set<Worker<?>> workers,
                                @NonNull final ThreadPool cynthiaThreadPool) {
        return new WorkerPool(cynthiaThreadPool, workers, id, () -> null);
    }

    public static WorkerPool of(@NonNull final String id,
                                @NonNull final Set<Worker<?>> workers,
                                @NonNull final Supplier<WorkerPool> next,
                                @NonNull final ThreadPool threadPool) {
        return new WorkerPool(threadPool, workers, id, next);
    }

    public final void process() {
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
