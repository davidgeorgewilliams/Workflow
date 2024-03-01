package com.davidgeorgewilliams.workflows;

import com.davidgeorgewilliams.workflows.workers.WorkerPool;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

@Accessors(fluent = true, chain = true)
@Builder(access = AccessLevel.PRIVATE)
@Value
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

    public void process() {
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
