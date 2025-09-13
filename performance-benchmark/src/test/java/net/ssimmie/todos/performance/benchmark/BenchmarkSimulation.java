package net.ssimmie.todos.performance.benchmark;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class BenchmarkSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .warmUp("http://localhost:8181/actuator")
            .baseUrl("http://localhost:8181")
            .acceptHeader("application/json")
            .shareConnections();

    ScenarioBuilder healthCheckJourney = scenario("Todos Health Check")
            .exec(HealthCheckAction.healthcheck());

    ScenarioBuilder coreJourney = scenario("Todos Core Journey")
            .exec(
                    RootResourceAction.queryRoot(),
                    ChecklistsResourceAction.createChecklist(),
                    ChecklistResourceAction.getChecklist()
            );

    {
        setUp(
                healthCheckJourney.injectOpen(constantUsersPerSec(1).during(Duration.ofMinutes(1))),
                coreJourney.injectOpen(constantUsersPerSec(30).during(Duration.ofMinutes(1)).randomized())
        ).protocols(httpProtocol)
                .assertions(
                        global().responseTime().max().lt(1500),
                        global().responseTime().percentile4().lt(900),
                        global().responseTime().percentile3().lte(250),
                        global().successfulRequests().percent().is(100.0)
                );
    }
}