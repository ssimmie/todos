## Todos

Todos API

![ci-build](https://github.com/ssimmie/todos/workflows/ci-build/badge.svg?branch=master)


## Observability

```docker run -d --name=prometheus -p 9090:9090 -v ../config/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml```

```docker run -d --name=grafana -p 3000:3000 grafana/grafana```
