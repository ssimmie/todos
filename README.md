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
