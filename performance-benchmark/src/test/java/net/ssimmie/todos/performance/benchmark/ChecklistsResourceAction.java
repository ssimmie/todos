package net.ssimmie.todos.performance.benchmark;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.ChainBuilder;
import java.util.UUID;

public class ChecklistsResourceAction {

  public static ChainBuilder createChecklist() {
    return exec(
        http("Create new checklist")
            .post("${checklistsUrl}")
            .body(
                StringBody(
                    String.format(
                        """
                        {
                            "name": "%s"
                        }
                        """,
                        UUID.randomUUID().toString())))
            .asJson()
            .header("Content-Type", "application/json")
            .header("Accept", "application/hal+json")
            .check(status().is(201))
            .check(header("Location").saveAs("checklistUrl"))
            .check(jsonPath("$._links.self.href").exists()));
  }
}
