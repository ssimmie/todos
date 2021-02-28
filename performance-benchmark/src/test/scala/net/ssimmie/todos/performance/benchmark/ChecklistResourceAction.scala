package net.ssimmie.todos.performance.benchmark

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.HeaderNames._
import io.gatling.http.HeaderValues.ApplicationJson
import io.gatling.http.Predef._


object ChecklistResourceAction {

  val getChecklist: ChainBuilder = exec(http("Retrieve existing checklist")
    .get("${checklistUrl}")
    .header(ContentType, ApplicationJson)
    .header(Accept, "application/hal+json")
    .check(status.is(200))
    .check(jsonPath("$._links.self.href").is("${checklistUrl}")))
}
