package com.davidgeorgewilliams.workflows.workers;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

@Accessors(fluent = true, chain = true)
@Builder(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@Value
public class WorkerException extends RuntimeException {
    private WorkerException() {
        super(new RuntimeException());
    }

    private WorkerException(@NonNull final Throwable t) {
        super(t);
    }

    public static WorkerException of(@NonNull final Throwable t) {
        return new WorkerException(t);
    }

    public static WorkerException of(@NonNull final Worker<?> worker) {
        return of(worker.throwable());
    }
}
