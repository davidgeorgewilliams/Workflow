package com.davidgeorgewilliams.workflows;

import com.davidgeorgewilliams.workflows.workers.WorkerPool;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter(value = AccessLevel.PUBLIC)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Workflow {
    String id;
    WorkerPool root;

    public static Workflow of(@NonNull final WorkerPool root) {
        final String id = UUID.randomUUID().toString();
        return Workflow.of(id, root);
    }

    public static Workflow of(@NonNull final String id,
                              @NonNull final WorkerPool root) {
        return new Workflow(id, root);
    }

    public final void process() {
        WorkerPool cursor = root();
        while (Objects.nonNull(cursor)) {
            cursor.process();
            final Supplier<WorkerPool> next = cursor.next();
            if (Objects.nonNull(next)) {
                cursor = next.get();
            } else {
                return;
            }
        }
    }
}
