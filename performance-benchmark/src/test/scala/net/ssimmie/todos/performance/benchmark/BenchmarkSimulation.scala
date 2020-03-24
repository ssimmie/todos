package net.ssimmie.todos.performance.benchmark

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class BenchmarkSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8181")
    .acceptHeader("application/json")

  val scn = scenario("Todos Actuator")
    .exec(http("healthcheck")
      .get("/actuator/health"))
    .pause(5)

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpProtocol)
}
