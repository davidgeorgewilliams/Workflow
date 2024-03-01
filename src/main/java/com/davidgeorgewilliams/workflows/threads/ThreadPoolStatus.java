package com.davidgeorgewilliams.workflows.threads;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Accessors(fluent = true, chain = true)
@Builder(access = AccessLevel.PRIVATE)
@Value
public class ThreadPoolStatus {
    int activeCount;
    int corePoolSize;
    int maximumPoolSize;
    int poolSize;
    int queueSize;
    long completedTaskCount;
    long keepAliveTime;
    long largestPoolSize;
    long taskCount;

    public static ThreadPoolStatus of(@NonNull final ThreadPool threadPool) {
        final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) threadPool.executorService();
        final int activeCount = threadPoolExecutor.getActiveCount();
        final int corePoolSize = threadPoolExecutor.getCorePoolSize();
        final int maximumPoolSize = threadPoolExecutor.getMaximumPoolSize();
        final int poolSize = threadPoolExecutor.getPoolSize();
        final int queueSize = threadPoolExecutor.getQueue().size();
        final long completedTaskCount = threadPoolExecutor.getCompletedTaskCount();
        final long keepAliveTime = threadPoolExecutor.getKeepAliveTime(TimeUnit.MILLISECONDS);
        final long largestPoolSize = threadPoolExecutor.getLargestPoolSize();
        final long taskCount = threadPoolExecutor.getTaskCount();
        return ThreadPoolStatus.builder()
                .activeCount(activeCount)
                .completedTaskCount(completedTaskCount)
                .corePoolSize(corePoolSize)
                .keepAliveTime(keepAliveTime)
                .largestPoolSize(largestPoolSize)
                .maximumPoolSize(maximumPoolSize)
                .poolSize(poolSize)
                .queueSize(queueSize)
                .taskCount(taskCount)
                .build();
    }
}
