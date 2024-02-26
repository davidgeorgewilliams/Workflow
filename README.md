# Workflows

Workflows is an advanced Java library that furnishes a high-level abstraction layer for parallel workflow management and
execution engine construction. It empowers developers to articulate and execute complex, dependent tasks in a concurrent
environment with ease and precision. Built on the foundation of modern concurrent programming paradigms, Workflows
streamlines the process of defining and managing task relationships and execution order through a domain-specific
language crafted for simplicity and power.

![Workflows Visual Representation](docs/Workflow.png)

## Introduction

At the heart of Workflows lies a suite of elegantly designed constructs: **Workflow** orchestrates sequences of
**WorkerPool** objects, enabling cascading task execution; **WorkerPool** serves as an innovative abstraction over
thread
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

To integrate `Workflows` into your Maven project, you'll first need to clone the repository and build the artifact
locally. Execute the following commands in your terminal:

```bash
git clone https://github.com/davidgeorgewilliams/Workflows.git
cd Workflows
mvn clean install
```

After successfully building the project, incorporate `Workflows` into your application by adding the following
dependency block to your project's `pom.xml` file:

```xml

<dependency>
    <groupId>com.davidgeorgewilliams</groupId>
    <artifactId>Workflows</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

This ensures that the locally built version of `Workflows` is recognized and used by your Maven project.

### Recommended JDK

For optimal performance and compatibility, it is recommended to
use [Azul Zulu 21](https://www.azul.com/downloads/?version=java-21-lts&package=jdk#zulu), provided
by [Azul Systems Inc.](https://www.azul.com/)

### Self-Contained Example

The following example demonstrates how to use Workflows to process a set of tasks in parallel, leveraging the power of
multi-threading to enhance performance.

Imagine you have a list of 100 mathematical operations that you need to perform - specifically, calculating the natural
logarithm of numbers 1 through 100. Using Workflows, you can efficiently distribute these calculations across multiple
threads, corresponding to the number of available processors on your system.

Here's a simple yet powerful example:

```java
final Set<Worker<?>> workers = new HashSet<>();
for(
int i = 0;
i< 100;i++){
final double value = 1.0 * (i + 1);
final Worker<Double> worker = Worker.of(() -> Math.log(value));
    workers.

add(worker);
}
final int processors = Runtime.getRuntime().availableProcessors();
final ThreadPool threadPool = ThreadPool.of(processors);
final WorkerPool workerPool = WorkerPool.of(workers, threadPool);
Workflow.

of(workerPool).

process();
for(
final Worker<?> worker :workers){
final double logValue = (double) worker.result();
final double value = Math.exp(logValue);
final ThreadLocalTime completed = worker.completed();
    log.

info(String.format("completed=%s value=%s logValue=%s", completed, value, logValue));
        }
        return 0;
```

In this code snippet:

We create a Worker for each operation, which contains the task to be executed - in this case, calculating the logarithm.
These `Worker` instances are added to a `WorkerPool`, signifying a group of tasks to be run together.
A `ThreadPool` manages the execution of these tasks across multiple threads.
Once the `WorkerPool` is instructed to process the tasks, each Worker executes its operation in parallel, utilizing the
full capabilities of the system's CPU.
After processing, we log the results of each task, including the completion time and the values calculated.

This example not only showcases the simplicity and elegance with which Workflows handles concurrent operations but also
highlights the ease with which computationally intensive tasks can be parallelized, resulting in significant performance
gains. It's an ideal framework for businesses and engineers looking to optimize their processing capabilities, reduce
latency, and achieve superior throughput in their applications.

## Class Overview and Usage

The `Workflows` library exposes a high-level API for constructing and executing workflows composed of dependent tasks,
enabling efficient parallel processing. The API is centered around four main
constructs: `Workflow`, `WorkerPool`, `Worker`, and `ThreadPool`.

### Workflow

The `Workflow` class in Workflows library offers a streamlined approach to orchestrating complex task sequences
within `WorkerPool` groups, ensuring orderly and dependent execution with dynamic, runtime-adjustable workflows. By
facilitating sequential and parallel processing through a unique identifier for each workflow, it simplifies the
construction and management of data processing pipelines and other sophisticated task sequences. This makes `Workflow`
an invaluable tool for developers requiring a high degree of control and efficiency in concurrent programming, perfectly
suited for applications demanding structured task execution and scalability.

### WorkerPool

The `WorkerPool` class encapsulates a sophisticated thread pool mechanism for executing `Worker` tasks in parallel,
enriched with 'after' semantics for defining task interdependencies and execution order. By allowing specification of
prerequisites for each task, it ensures a logical and efficient progression of work. Constructed dynamically with a
unique identifier and an optional linkage to subsequent `WorkerPool`s via the `next` supplier, `WorkerPool` facilitates
complex workflows with ease. Its processing logic intelligently manages task readiness and execution mode, leveraging a
combination of immediate execution and `CompletableFuture` for asynchronous tasks, thereby achieving optimal utilization
of computing resources in concurrent applications.

### Worker

The `Worker` class is a key component of the Workflows library, designed to encapsulate individual units of work
alongside their execution metadata, such as start, completion times, and outcomes. It leverages Java's `Supplier`
interface to define tasks that can be executed concurrently, capturing the results and handling any exceptions that
arise. Through attributes like `parallel` and dependency management via the `after` set, `Worker` allows for
fine-grained control over task execution order and concurrency. Each `Worker` is uniquely identified and can track its
lifecycle events, from submission to completion, facilitating detailed monitoring and analysis of concurrent operations.
The class provides methods for task readiness checks and processing, ensuring that dependencies are resolved before
execution and exceptions are appropriately managed, making `Worker` an essential tool for implementing complex,
reliable, and efficient concurrent workflows.

### ThreadPool

The `ThreadPool` class serves as an intuitive interface to Java's concurrent execution frameworks,
specifically `ExecutorService` and `CompletableFuture`. It abstracts the complexity of managing threads and executing
asynchronous tasks, allowing developers to focus on the logic of their applications rather than the intricacies of
thread management. With the capability to create a pool of threads specified by the number of threads, `ThreadPool`
facilitates efficient task execution by distributing tasks across the available threads. This class supports the
submission of both `Runnable` and `Supplier<T>` tasks, returning a `Future<?>` or `CompletableFuture<T>` respectively,
thus enabling flexible execution strategies including synchronous waits or non-blocking callbacks.
Essentially, `ThreadPool` enhances the scalability and responsiveness of applications by optimizing resource utilization
and parallelizing task execution.

## About the Author

**Name**: David George Williams  
**Location**: United Kingdom  
**Email**: david@davidgeorgewilliams.com  
**Website**: [davidgeorgewilliams.com](https://davidgeorgewilliams.com)  
**LinkedIn**: [linkedin.com/in/david-george-williams-1025804b](https://linkedin.com/in/david-george-williams-1025804b)  
**Upwork**: [upwork.com/freelancers/~01f6c903d62beb9032](https://upwork.com/freelancers/~01f6c903d62beb9032)  
**Fiverr**: [fiverr.com/davidgw_uk/advance-ethical-ai](https://fiverr.com/davidgw_uk/advance-ethical-ai)

I bring a unique blend of analytical prowess and human-centered insight to the field of AI and computer science. My
academic background includes graduating from the MIT Center for Transportation & Logistics, where I specialized in the
intricate dynamics of international vaccine supply chains, influencing strategies adopted by organizations such as the
US CDC for vaccine distribution. With a solid foundation in pure mathematics from the University of California, Santa
Cruz, I exhibit a commitment to rigorous analytical thinking.

My professional journey extends beyond academia, with impactful contributions made at renowned companies like Apple and
PayPal, where I have demonstrated both my ability to collaborate effectively within teams and my capacity for
innovation. Beyond the realm of algorithms and numbers, I am driven by a core ethos of utilizing technology to effect
meaningful and enduring change in the world.

## Special Thanks

I extend my sincere gratitude to Oliver Saleh, Deepak Kapur, and the Applied Machine Learning team at Apple Inc. for
their invaluable support and encouragement throughout the research and development of this groundbreaking paradigm in
concurrent programming.

Oliver and Deepak's dedication, expertise, and creative contributions were instrumental in transforming the core concept
of a Workflow into a tangible reality. Their passion, innovation, and tireless efforts have laid the groundwork for the
advancement of high-performance, reliable, and efficient systems.

### Oliver Saleh

Oliver Saleh, an Expert Associate Partner in Data Science and Machine Learning, has a remarkable track record of
delivering innovative solutions at internet scale. With over 15 years of experience in the industry, Oliver has held
pivotal roles at leading companies in the AI domain, including Microsoft and Apple. His expertise spans various domains
such as generative models, fraud detection, natural language processing, computer vision, and distributed systems. At
Apple, Oliver's focus on Applied Machine Learning & Strategic Health Initiatives significantly contributed to the
company's advancements. [Oliver's LinkedIn Profile](https://www.linkedin.com/in/oliver-s-95103610/)

### Deepak Kapur

Deepak Kapur, the Co-founder and CEO of Qualification AI, brings extensive experience from his tenure at Google and
Apple. Deepak's innovative work at Qualification AI has revolutionized sales processes by providing AI-driven sales
coaching and personalized roleplays. During his time at Apple, Deepak led a team of talented engineers responsible for
developing Apple’s 24/7 hardware diagnostics platform, which plays a crucial role in preventing warranty fraud. His
technical expertise and leadership have been pivotal in driving platform changes and ensuring the efficacy of diagnostic
systems across Apple's global retail network. [Deepak's LinkedIn Profile](https://www.linkedin.com/in/deepakkapur/)

Their contributions have not only enhanced the development of this project but have also inspired a new wave of
innovation in the field of concurrent programming.

## Dedication

This library is dedicated to Steve Jobs and is officially published on his birthday as a tribute to his enduring legacy
in technology and innovation.

Steve Jobs, the co-founder of Apple Computer, Inc. (now Apple Inc.), was a charismatic pioneer of the personal computer
era. Raised by adoptive parents in Cupertino, California, located in what is now known as Silicon Valley, Jobs' journey
encompassed various interests from engineering to spirituality. He dropped out of Reed College, worked at Atari
Corporation as a video game designer, and embarked on a pilgrimage to India before returning to Silicon Valley in 1974.
There, he reconnected with Stephen Wozniak, and together they founded Apple Computer in 1976, introducing the Apple I
and later the highly successful Apple II.

Jobs's visionary leadership led Apple to become synonymous with innovation, evident in groundbreaking products like the
Macintosh, which featured a graphical user interface, and later the iMac, iPod, iPhone, and iPad. Despite setbacks,
including his temporary departure from Apple and subsequent founding of NeXT Inc. and Pixar, Jobs' return to Apple in
1997 marked a renaissance for the company.

His relentless pursuit of excellence and belief in the power of innovation continues to inspire entrepreneurs and
business leaders worldwide. In his honor, here are 10 quotes from Steve Jobs that resonate with the entrepreneurial
spirit:

1. **"Innovation distinguishes between a leader and a follower."**
2. **"Your time is limited, so don’t waste it living someone else’s life."**
3. **"Don’t let the noise of others’ opinions drown out your own inner voice."**
4. **"You can’t connect the dots looking forward; you can only connect them looking backwards. So you have to trust that
   the dots will somehow connect in your future."**
5. **"Be a yardstick of quality. Some people aren’t used to an environment where excellence is expected."**
6. **"Stay hungry. Stay foolish."**
7. **"I'm convinced that about half of what separates the successful entrepreneurs from the nonsuccessful ones is pure
   perseverance."**
8. **"You can't just ask customers what they want and then try to give that to them. By the time you get it built,
   they'll want something new."**
9. **"People think focus means saying yes to the thing you've got to focus on. It means saying no to the hundred other
   good ideas that there are. You have to pick carefully."**
10. **"We’re here to put a dent in the universe. Otherwise why else even be here?"**

Steve Jobs' indelible mark on the world serves as a reminder of the transformative power of vision, perseverance, and
innovation.