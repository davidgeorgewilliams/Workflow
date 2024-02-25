package com.davidgeorgewilliams.workflows.threads;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

@Accessors(fluent = true)
@Builder(access = AccessLevel.PRIVATE)
@Value
public class ThreadPool {
    ExecutorService executorService;

    public static ThreadPool of(final int numThreads) {
        final ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        return ThreadPool.builder()
                .executorService(executorService)
                .build();
    }

    public Future<?> submit(@NonNull final Runnable runnable) {
        return executorService().submit(runnable);
    }

    public <T> CompletableFuture<T> submit(@NonNull final Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, executorService());
    }
}
