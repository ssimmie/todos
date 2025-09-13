package net.ssimmie.todos.performance.benchmark;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class ChecklistResourceAction {

    public static ChainBuilder getChecklist() {
        return exec(http("Retrieve existing checklist")
                .get("${checklistUrl}")
                .header("Content-Type", "application/json")
                .header("Accept", "application/hal+json")
                .check(status().is(200))
                .check(jsonPath("$._links.self.href").exists())
        );
    }
}