## Todos

Todos API

![ci-build](https://github.com/ssimmie/todos/workflows/ci-build/badge.svg?branch=master)
![daily-build](https://github.com/ssimmie/todos/workflows/daily-build/badge.svg)
[![license](http://img.shields.io/badge/license-MIT-brightgreen.svg)](https://github.com/ssimmie/todos/blob/master/LICENSE)

### Running locally

```./mvnw install && docker-compose -f config/docker-compose.yml up```

* [Todos Application Actuator](http://localhost:8181/actuator)
* [Todos Application API Docs](http://localhost:8181/docs/index.html)
* [Prometheus UI](http://localhost:9090)
* [Grafana](http://localhost:3000)
* [Grafana Micrometer Dashboard](http://localhost:3000/d/9IsnyquWz/jvm-micrometer?orgId=1&refresh=5s)


### Development Process

See the [ALM](https://ssimmie.github.io/application-lifecycle-management/) guidelines

#### Continuous Integration Stage Gates

* Verify source format matches Google Style; [FMT](https://github.com/coveooss/fmt-maven-plugin)
* Compile without warning; [ErrorProne](https://errorprone.info/)
* Unit test; [Surefire](http://maven.apache.org/surefire/maven-surefire-plugin/) and ensure coverage is 100%; [Jacoco](https://www.eclemma.org/jacoco/trunk/doc/maven.html)
* Mutation test unit tests; [PITest](https://pitest.org/) 
* Integration test; [Failsafe](https://maven.apache.org/surefire/maven-failsafe-plugin/)
* Ensure dependencies free from vulnerabilities; [OWASP Dependency Check](https://github.com/jeremylong/DependencyCheck)
* Analyze source for defects; [PMD](https://pmd.github.io/), [Spotbugs](https://spotbugs.github.io/), [Checkstyle](https://checkstyle.sourceforge.io/)
* Generate API Documentation; [Spring REST Docs](https://spring.io/projects/spring-restdocs)
* Acceptance test; [CucumberJVM](https://cucumber.io/)
* Performance Benchmark test; [Gatling](https://gatling.io/open-source/)
* Publish JARs; [GitHub Packages](https://github.com/features/packages)
* Publish sensible; [Distroless](https://github.com/GoogleContainerTools/distroless) Docker Images; [GitHub Packages](https://github.com/features/packages)
