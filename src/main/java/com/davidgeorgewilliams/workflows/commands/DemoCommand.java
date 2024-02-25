package com.davidgeorgewilliams.workflows.commands;

import com.davidgeorgewilliams.workflows.Workflow;
import com.davidgeorgewilliams.workflows.threads.ThreadLocalTime;
import com.davidgeorgewilliams.workflows.threads.ThreadPool;
import com.davidgeorgewilliams.workflows.workers.Worker;
import com.davidgeorgewilliams.workflows.workers.WorkerPool;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

@Accessors(fluent = true)
@CommandLine.Command(description = "Workflow demo command.", name = "Demo")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
@Value
public class DemoCommand implements Callable<Integer> {
    @Override
    @SneakyThrows
    public Integer call() {
        final Set<Worker<?>> workers = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            final double value = 1.0 * (i + 1);
            final Worker<Double> worker = Worker.of(() -> Math.log(value));
            workers.add(worker);
        }
        final int processors = Runtime.getRuntime().availableProcessors();
        final ThreadPool threadPool = ThreadPool.of(processors);
        final WorkerPool workerPool = WorkerPool.of(workers, threadPool);
        Workflow.of(workerPool).process();
        for (final Worker<?> worker : workers) {
            final double logValue = (double) worker.result();
            final double value = Math.exp(logValue);
            final ThreadLocalTime completed = worker.completed();
            log.info(String.format("completed=%s value=%s logValue=%s", completed, value, logValue));
        }
        return 0;
    }
}
