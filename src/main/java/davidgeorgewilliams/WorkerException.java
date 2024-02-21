package davidgeorgewilliams;

import lombok.NonNull;

public class WorkerException extends RuntimeException {
    private WorkerException(@NonNull final Throwable t) {
        super(t);
    }

    public static WorkerException of(@NonNull final Throwable t) {
        return new WorkerException(t);
    }

    public static WorkerException of(@NonNull final Worker<?> worker) {
        return WorkerException.of(worker.throwable());
    }
}
