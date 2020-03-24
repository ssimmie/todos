package net.ssimmie.todos.performance.benchmark

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.HeaderNames._
import io.gatling.http.HeaderValues.ApplicationJson
import io.gatling.http.Predef._

object HealthCheckAction {

  val healthcheck: ChainBuilder = exec(http("Healthcheck application")
    .get("/actuator/health")
    .header(ContentType, ApplicationJson)
    .header(Accept, ApplicationJson)
    .check(status.is(200))
    .check(jsonPath("$.status").is("UP")))

}
