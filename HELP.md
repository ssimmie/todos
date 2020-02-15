# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/maven-plugin/)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/htmlsingle/#production-ready)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

### Building and Running

```shell script
./mvnw clean install
mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
docker build -t net.ssimmie.todos/application .
docker run -p 9090:9090 net.ssimmie.todos/application:latest
```
