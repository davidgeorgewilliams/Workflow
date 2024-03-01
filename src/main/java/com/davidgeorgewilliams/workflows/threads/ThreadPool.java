package com.davidgeorgewilliams.workflows.threads;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

@Accessors(fluent = true, chain = true)
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

    public <T> CompletableFuture<T> submit(@NonNull final Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, executorService());
    }
}
