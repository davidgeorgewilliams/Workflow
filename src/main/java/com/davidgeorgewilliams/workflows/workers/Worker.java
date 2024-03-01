package com.davidgeorgewilliams.workflows.workers;

import com.davidgeorgewilliams.workflows.threads.ThreadLocalTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.NonFinal;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@Accessors(fluent = true, chain = true)
@Builder(access = AccessLevel.PRIVATE)
@Value
public class Worker<T> {
    @NonFinal @Setter ThreadLocalTime completed;
    @NonFinal @Setter ThreadLocalTime finished;
    @NonFinal @Setter ThreadLocalTime started;
    @NonFinal @Setter ThreadLocalTime submitted;
    @NonFinal @Setter T result;
    @NonFinal @Setter Throwable throwable;
    @NonFinal @Setter boolean parallel;
    @NonNull Set<Worker<?>> after;
    @NonNull String id;
    @NonNull Supplier<T> supplier;
    ThreadLocalTime created = ThreadLocalTime.of();

    public static <T> Worker<T> of(@NonNull final Supplier<T> supplier) {
        return Worker.of(UUID.randomUUID().toString(), supplier);
    }

    public static <T> Worker<T> of(@NonNull final String id,
                                   @NonNull final Supplier<T> supplier) {
        return Worker.of(new HashSet<>(), id, supplier);
    }

    public static <T> Worker<T> of(@NonNull final Set<Worker<?>> after,
                                   @NonNull final String id,
                                   @NonNull final Supplier<T> supplier) {
        return Worker.of(after, id, supplier, true);
    }

    public static <T> Worker<T> of(@NonNull final Set<Worker<?>> after,
                                   @NonNull final String id,
                                   @NonNull final Supplier<T> supplier,
                                   final boolean parallel) {
        return Worker.<T>builder()
                .after(after)
                .id(id)
                .parallel(parallel)
                .supplier(supplier)
                .build();
    }


    public final boolean isReady() {
        for (final Worker<?> worker : after()) {
            if (Objects.nonNull(worker.throwable())) {
                throw WorkerException.of(worker);
            }
            if (Objects.isNull(worker.finished())) {
                return false;
            }
        }
        return true;
    }

    public final T process() {
        try {
            started(ThreadLocalTime.of());
            result(supplier().get());
            finished(ThreadLocalTime.of());
            return result();
        } catch (final Throwable t) {
            throwable(t);
            throw WorkerException.of(t);
        }
    }

    public void after(@NonNull final Worker<T> worker) {
        this.after().add(worker);
    }

    public void after(@NonNull final Set<Worker<?>> workers) {
        for (final Worker<?> worker : workers) {
            this.after().add(worker);
        }
    }
}
