package davidgeorgewilliams;

import davidgeorgewilliams.threads.ThreadLocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter(value = AccessLevel.PUBLIC)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Worker<T> {
    @NonFinal @Setter ThreadLocalTime completed;
    @NonFinal @Setter ThreadLocalTime finished;
    @NonFinal @Setter ThreadLocalTime started;
    @NonFinal @Setter ThreadLocalTime submitted;
    @NonFinal @Setter T result;
    @NonFinal @Setter Throwable throwable;
    @NonFinal @Setter boolean parallel = true;
    @NonNull Set<Worker<?>> after;
    @NonNull String id;
    @NonNull Supplier<T> supplier;
    ThreadLocalTime created = ThreadLocalTime.of();

    public static <T> Worker<T> of(@NonNull final Supplier<T> supplier) {
        return Worker.of(UUID.randomUUID().toString(), supplier);
    }

    public static <T> Worker<T> of(@NonNull final String id,
                                   @NonNull final Supplier<T> supplier) {
        return Worker.of(Set.of(), id, supplier);
    }

    public static <T> Worker<T> of(@NonNull final Set<Worker<?>> after,
                                   @NonNull final String id,
                                   @NonNull final Supplier<T> supplier) {
        return new Worker<>(after, id, supplier);
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
}
