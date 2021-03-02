## Todos

Playground for me to try out various libraries, tools, and techniques.

![ci-build](https://github.com/ssimmie/todos/workflows/ci-build/badge.svg?branch=master)
![daily-build](https://github.com/ssimmie/todos/workflows/daily-build/badge.svg)
![coverage](https://img.shields.io/badge/coverage-100%25-brightgreen)
[![license](http://img.shields.io/badge/license-MIT-brightgreen.svg)](https://github.com/ssimmie/todos/blob/master/LICENSE)

## Development Approach 

- [x] Minimal developer pre-requisites; [Maven Wrapper](https://maven.apache.org/plugins/maven-wrapper-plugin/)
- [x] Verify source format matches Google Style; [FMT](https://github.com/coveooss/fmt-maven-plugin) or [Spotless](https://github.com/diffplug/spotless) 
- [x] Compilation without warning; [ErrorProne](https://errorprone.info/)
- [x] Test Driven Development; unit testing with coverage backstop; [Surefire](http://maven.apache.org/surefire/maven-surefire-plugin/); [Jacoco](https://www.eclemma.org/jacoco/trunk/doc/maven.html); [JUnit 5](https://junit.org/junit5/); [Assertj](https://assertj.github.io/doc/); [EqualsVerifier](https://jqno.nl/equalsverifier/); [ToStringVerifier](https://github.com/jparams/to-string-verifier)
- [x] Mutation testing; ensuring high-quality unit tests [PITest](https://pitest.org/) or [Stryker Mutator](https://stryker-mutator.io/)
- [ ] Consumer-Driven Contract testing; ensure independent paths to production
- [x] Document your architecture with tests; [ArchUnit](https://www.archunit.org/)
- [x] Integration testing; [Failsafe](https://maven.apache.org/surefire/maven-failsafe-plugin/)
- [ ] API documentation; dependable documentation extracted from proven integration tests; [Spring REST Docs](https://spring.io/projects/spring-restdocs). Removed previous version as integration tests had to be run with unit tests and caused issues with coverage.
- [x] Static analysis; ensure source free from known defects; [PMD](https://pmd.github.io/), [Spotbugs](https://spotbugs.github.io/), [Checkstyle](https://checkstyle.sourceforge.io/)
- [x] Software composition analysis; ensure dependencies free from vulnerabilities; [OWASP Dependency Check](https://github.com/jeremylong/DependencyCheck)
- [x] Acceptance testing; ensure system matches desired behaviours; [CucumberJVM](https://cucumber.io/) [Docker Compose Plugin](https://github.com/syncdk/docker-compose-maven-plugin) [Await Plugin](https://github.com/slem1/await-maven-plugin) 
- [x] Performance Benchmark testing; ensure local test matches representative performance goals; [Gatling](https://gatling.io/open-source/) or [Locust](https://locust.io/) or [K6](https://k6.io/open-source)
- [ ] Resilience testing; ensure fault tolerance; circuit-breakers
- [x] Containerization; lightweight, reliable distribution; [Distroless](https://github.com/GoogleContainerTools/distroless) Docker Images
- [x] Distribution management; [GitHub Packages](https://github.com/features/packages)
- [ ] Technical documentation; lightweight architecture decision records; [ADR](https://adr.github.io/)
- [x] Product documentation; [Github Pages](https://pages.github.com/); [Just The Docs](https://pmarsceill.github.io/just-the-docs/)
