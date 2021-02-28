package net.ssimmie.todos.performance.benchmark

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.HeaderNames._
import io.gatling.http.HeaderValues.ApplicationJson
import io.gatling.http.Predef._

import java.util.UUID

object ChecklistsResourceAction {

  val createChecklist: ChainBuilder = exec(http("Create new checklist")
    .post("${checklistsUrl}")
    .body(
      StringBody(
        s"""
           |{
           |"name": "${UUID.randomUUID().toString}"
           |}
           |""".stripMargin
      )
    ).asJson
    .header(ContentType, ApplicationJson)
    .header(Accept, "application/hal+json")
    .check(status.is(201))
    .check(header("Location").saveAs("checklistUrl"))
    .check(jsonPath("$._links.self.href").is("${checklistUrl}")))
}
