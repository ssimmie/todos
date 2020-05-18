package net.ssimmie.todos.performance.benchmark

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._

class BenchmarkSimulation extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http
    .warmUp("http://localhost:8181/actuator")
    .baseUrl("http://localhost:8181")
    .acceptHeader("application/json")

  val loadBalancers: ScenarioBuilder = scenario("Todos Core Journey")
    .exec(HealthCheckAction.healthcheck)
    .exec(RootResourceAction.queryRoot)

  setUp(
    loadBalancers.inject(constantUsersPerSec(10) during (1 minutes))
  ).protocols(
    httpProtocol
  ).assertions(
    global.responseTime.percentile3.lte(15),
    global.responseTime.percentile4.lt(50),
    global.successfulRequests.percent.is(100)
  )
}
