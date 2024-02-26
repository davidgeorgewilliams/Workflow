package com.davidgeorgewilliams.workflows;

import com.davidgeorgewilliams.workflows.workers.Worker;
import com.davidgeorgewilliams.workflows.workers.WorkerPool;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AfterSemanticsTest {

    @Test
    public void testAfterSemantics() {
        final String messageTemplate = "Hello from worker%s";
        final Worker<String> workerA = Worker.of(() -> String.format(messageTemplate, "A"));
        final Worker<String> workerB = Worker.of(() -> String.format(messageTemplate, "B"));
        final Worker<String> workerC = Worker.of(() -> String.format(messageTemplate, "C"));
        final Worker<String> workerD = Worker.of(() -> String.format(messageTemplate, "D"));
        workerB.after(workerA);
        workerC.after(workerA);
        workerD.after(Set.of(workerB, workerC));
        final WorkerPool workerPool = WorkerPool.of(Set.of(workerA, workerB, workerC, workerD));
        final Workflow workflow = Workflow.of(workerPool);
        workflow.process();
        assertEquals(workerB.completed().compareTo(workerA.completed()), 1);
        assertEquals(workerC.completed().compareTo(workerA.completed()), 1);
        assertEquals(workerD.completed().compareTo(workerB.completed()), 1);
        assertEquals(workerD.completed().compareTo(workerC.completed()), 1);
    }
}
