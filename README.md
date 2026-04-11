# Market Pulse

Real-time market analytics backend service built with Java 17 and Spring Boot 3.
Demonstrates producer-consumer concurrency patterns, thread-safe in-memory storage,
and stream-based analytics — without external dependencies.

## Architecture

- **Producer:** `TickGenerator` — `@Scheduled` job emitting simulated price ticks
- **Buffer:** Bounded `LinkedBlockingQueue<Tick>` (capacity 1000) for backpressure
- **Consumer:** `TickConsumer` — dedicated worker thread draining via `take()`
- **Lifecycle:** `@PostConstruct` startup + `@PreDestroy` graceful shutdown with thread interruption

## Tech Stack

- Java 17, Spring Boot 3.5
- `java.util.concurrent` — `BlockingQueue`, `ThreadLocalRandom`, `volatile` flags
- Maven

## Status

🚧 In active development. Roadmap:
- [x] Producer-consumer ingestion pipeline
- [ ] Per-symbol rolling window store (`ConcurrentHashMap` + bounded `Deque`)
- [ ] Analytics APIs: moving average, volatility, top-K gainers
- [ ] REST endpoints with custom `ThreadPoolTaskExecutor`
- [ ] JUnit + Mockito concurrency tests with `CountDownLatch`

## Run

```bash
./mvnw spring-boot:run
```
