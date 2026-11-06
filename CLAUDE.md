# CLAUDE.md

Guidance for Claude Code when working in this repository.

## Project Overview

`java-ai-assist` (artifact) — a Spring Boot **3.2.0** REST "hello world" service.
- **Group / artifact:** `org.example:java-ai-assist`
- **Base package:** `org.example` (entrypoint: `HelloWorldApplication`)
- **Endpoints:** `GET /api/hello?name=…`, `GET /api/greeting`, plus actuator `/actuator/health`
- **Frontend:** static `src/main/resources/static/index.html` (AngularJS) served by Spring

## Toolchain

- **Java 25** — compile and run against the JDK 25 toolchain.
  - NOTE: `pom.xml` currently pins `<java.version>17</java.version>` and `maven.compiler.{source,target}=17`.
    To target Java 25, bump all three properties to `25` (see "Conventions" below).
- **Maven 3.9+** — the sole build tool. There is no Gradle, no wrapper commit yet.
- **JUnit 5 (Jupiter)** — via `spring-boot-starter-test` (which bundles JUnit 5, Mockito, AssertJ).

## Common Commands

```bash
# Compile
mvn compile

# Run all tests (JUnit 5)
mvn test

# Run a single test class
mvn test -Dtest=HelloControllerTest

# Run a single test method
mvn test -Dtest=HelloControllerTest#helloReturnsMessage

# Package the runnable JAR (skips tests)
mvn clean package -DskipTests

# Run the application (starts on :8080)
mvn spring-boot:run

# Run a packaged jar
java -jar target/java-ai-assist-1.0-SNAPSHOT.jar
```

## Testing (JUnit 5)

- Tests live in `src/test/java`, mirroring `org.example`.
- Framework: **JUnit 5 (Jupiter)** — use `org.junit.jupiter.api.Test`, `org.junit.jupiter.api.*`.
  Do NOT write JUnit 4 (`org.junit.Test`) tests.
- Spring Boot test slices are available:
  - `@SpringBootTest` for full context, `@WebMvcTest` for controller/HTTP tests,
    `@SpringBootTest` + `TestRestTemplate` for integration.
- AssertJ is preferred (`assertThat(...)`), included via the starter.
- Always run `mvn test` before considering a change complete.

## Conventions

- Java 25 language features are allowed where they improve clarity (records, sealed types,
  pattern matching for `switch`, text blocks, preview features only if enabled).
- Prefer immutability: use `record` for value objects like `Greeting` when refactoring.
- Keep REST responses as DTOs under `org.example` (e.g. `Greeting`); avoid returning raw `Map`s for new endpoints.
- Configuration lives in `src/main/resources/application.properties` — do not hardcode ports/endpoints.
- Follow existing Spring idioms: `@RestController`, constructor injection, `@RequestParam` with defaults.

## Project Layout

```
src/main/java/org/example/   application classes
src/main/resources/          application.properties, static/ (frontend)
src/test/java/org/example/   JUnit 5 tests (mirror main packages)
pom.xml                      Maven build (Spring Boot parent)
```

## Notes for Claude

- Before changing the build, re-read `pom.xml` — the Spring Boot parent manages most dependency versions.
- If you bump to Java 25, update the three `pom.xml` properties together and confirm `java -version` is 25 on the CI/local toolchain.
- Keep responses concise; the app is intentionally small.
