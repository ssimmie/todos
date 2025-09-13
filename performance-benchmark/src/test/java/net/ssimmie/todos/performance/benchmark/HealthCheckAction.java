package net.ssimmie.todos.performance.benchmark;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class HealthCheckAction {

    public static ChainBuilder healthcheck() {
        return exec(http("Healthcheck application")
                .get("/actuator/health")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .check(status().is(200))
                .check(jsonPath("$.status").is("UP"))
        );
    }
}