package net.ssimmie.todos.performance.benchmark

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.HeaderNames._
import io.gatling.http.HeaderValues.ApplicationJson
import io.gatling.http.Predef._

object RootResourceAction {

  val queryRoot: ChainBuilder = exec(http("Query API root")
    .get("/")
    .header(ContentType, ApplicationJson)
    .header(Accept, "application/hal+json")
    .check(status.is(200))
    .check(jsonPath("$._links.self.href").is("http://localhost:8181/"))
    .check(jsonPath("$._links.checklists.href").saveAs("checklistsUrl")))
}
