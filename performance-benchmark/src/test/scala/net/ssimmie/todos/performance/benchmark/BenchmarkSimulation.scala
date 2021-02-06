package net.ssimmie.todos.performance.benchmark

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import net.ssimmie.todos.performance.benchmark.ChecklistsResourceAction.createChecklist
import net.ssimmie.todos.performance.benchmark.HealthCheckAction.healthcheck
import net.ssimmie.todos.performance.benchmark.RootResourceAction.queryRoot

import scala.concurrent.duration._

class BenchmarkSimulation extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http
    .warmUp("http://localhost:8181/actuator")
    .baseUrl("http://localhost:8181")
    .acceptHeader("application/json")
    .shareConnections

  val load: ScenarioBuilder = scenario("Todos Core Journey")
    .exec(healthcheck, queryRoot, createChecklist)

  setUp(
    load.inject(constantUsersPerSec(30).during(1.minutes).randomized)
  ).protocols(
    httpProtocol
  ).assertions(
    global.responseTime.max.lt(1000),
    global.responseTime.percentile4.lt(50),
    global.responseTime.percentile3.lte(15),
    global.successfulRequests.percent.is(100)
  )
}
