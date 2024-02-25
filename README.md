# Workflows

Workflows is an advanced Java library that furnishes a high-level abstraction layer for parallel workflow management and
execution engine construction. It empowers developers to articulate and execute complex, dependent tasks in a concurrent
environment with ease and precision. Built on the foundation of modern concurrent programming paradigms, Workflows
streamlines the process of defining and managing task relationships and execution order through a domain-specific
language crafted for simplicity and power.

![Workflows Visual Representation](docs/Workflow.png)

## Introduction

At the heart of Workflows lies a suite of elegantly designed constructs: **Workflow** orchestrates sequences of **WorkerPool** objects, enabling cascading task execution; **WorkerPool** serves as an innovative abstraction over thread
pools, utilizing 'after' semantics for defining task dependencies and ensuring logical execution order; **Worker**
encapsulates tasks with execution metadata, leveraging the `Supplier` interface for clear task definitions and detailed
execution tracking; and **ThreadPool** streamlines asynchronous task management, providing a simplified interface for
Java's `ExecutorService` and `CompletableFuture` APIs.

Workflows is designed with performance, scalability, and ease of use in mind, providing a robust toolkit for developers
building complex applications requiring sophisticated concurrency controls. Whether you're developing high-throughput
data processing systems, real-time computation engines, or complex application logic, Workflows offers the constructs
and control you need to build reliable and efficient concurrent applications.

This library is the culmination of dedicated research and innovation in the field of concurrent programming. It stands
as a testament to the collaborative spirit of the development community and is dedicated to visionaries who push the
boundaries of technology.

## Getting Started Guide

This guide will help you get started with the `Workflows` library, a sophisticated domain-specific language for
creating, managing, and executing parallel workflows in Java applications.

### Maven Coordinate

To include `Workflows` in your Maven project, add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.davidgeorgewilliams</groupId>
    <artifactId>Workflows</artifactId>
    <version>1.0</version>
</dependency>
```

### Recommended JDK

For optimal performance and compatibility, it is recommended to
use [Azul Zulu 21](https://www.azul.com/downloads/?version=java-21-lts&package=jdk#zulu), provided
by [Azul Systems Inc.](https://www.azul.com/)

## Self-Contained Example

The following example demonstrates how to use Workflows to process a set of tasks in parallel, leveraging the power of multi-threading to enhance performance.

Imagine you have a list of 100 mathematical operations that you need to perform - specifically, calculating the natural logarithm of numbers 1 through 100. Using Workflows, you can efficiently distribute these calculations across multiple threads, corresponding to the number of available processors on your system.

Here's a simple yet powerful example:

```java
final Set<Worker<?>> workers = new HashSet<>();
for (int i = 0; i < 100; i++) {
    final double value = 1.0 * (i + 1);
    final Worker<Double> worker = Worker.of(() -> Math.log(value));
    workers.add(worker);
}
final int processors = Runtime.getRuntime().availableProcessors();
final ThreadPool threadPool = ThreadPool.of(processors);
final WorkerPool workerPool = WorkerPool.of(workers, threadPool);
workerPool.process();
for (final Worker<?> worker : workers) {
    final double logValue = (double) worker.result();
    final double value = Math.exp(logValue);
    final ThreadLocalTime completed = worker.completed();
    log.info(String.format("completed=%s value=%s logValue=%s", completed, value, logValue));
}
```
In this code snippet:

We create a Worker for each operation, which contains the task to be executed - in this case, calculating the logarithm.
These `Worker` instances are added to a `WorkerPool`, signifying a group of tasks to be run together.
A `ThreadPool` manages the execution of these tasks across multiple threads.
Once the `WorkerPool` is instructed to process the tasks, each Worker executes its operation in parallel, utilizing the full capabilities of the system's CPU.
After processing, we log the results of each task, including the completion time and the values calculated.

This example not only showcases the simplicity and elegance with which Workflows handles concurrent operations but also highlights the ease with which computationally intensive tasks can be parallelized, resulting in significant performance gains. It's an ideal framework for businesses and engineers looking to optimize their processing capabilities, reduce latency, and achieve superior throughput in their applications.

## Class Overview and Usage

The `Workflows` library exposes a high-level API for constructing and executing workflows composed of dependent tasks,
enabling efficient parallel processing. The API is centered around four main
constructs: `Workflow`, `WorkerPool`, `Worker`, and `ThreadPool`.

### Workflow

A `Workflow` represents a cascade of `WorkerPool` objects, chained together. Workflows manage the flow of execution and
ensure that each `WorkerPool` is processed in the correct sequence.

### WorkerPool

The `WorkerPool` is an abstraction over thread pools, enabling parallel execution of `Worker` tasks. It introduces the '
after' semantics to specify dependencies between workers, ensuring tasks are executed in logical order.

### Worker

A `Worker` wraps a Java `Supplier` object along with execution metadata such as timings and outcomes. It allows for the
collection of results from concurrent operations and handles any exceptions that may occur during execution.

### ThreadPool

`ThreadPool` simplifies interaction with Java's `ExecutorService` and `CompletableFuture` APIs, providing a
straightforward way to manage asynchronous task submission and execution.

## Author

David George Williams  
Email: david@davidgeorgewilliams.com

## Special Thanks

Special thanks to Oliver Saleh, Deepak Kapur, and the Applied Machine Learning team at Apple Inc. for their support and
encouragement during the research and development of this new paradigm in concurrent programming. 

## Dedication

This library is dedicated to Steve Jobs and officially published on his birthday, as a tribute to his enduring legacy in
technology and innovation.
