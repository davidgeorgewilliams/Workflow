# Workflows

![Workflows Visual Representation](docs/Workflow.png)

## About Workflows

Workflows is an advanced Java library that furnishes a high-level abstraction layer for parallel workflow management and
execution engine construction. It empowers developers to articulate and execute complex, dependent tasks in a concurrent
environment with ease and precision. Built on the foundation of modern concurrent programming paradigms, Workflows
streamlines the process of defining and managing task relationships and execution order through a domain-specific
language crafted for simplicity and power.

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

## API Overview

The `Workflows` library exposes a high-level API for constructing and executing workflows composed of dependent tasks,
enabling efficient parallel processing. The API is centered around four main
constructs: `Workflow`, `WorkerPool`, `Worker`, and `ThreadPool`.

## Class Overview and Usage

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
encouragement during the research and development of this new paradigm in concurrent programming. This library is
affectionately dedicated to Steve Jobs and officially published on his birthday, as a tribute to his enduring legacy in
technology and innovation.
