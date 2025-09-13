package net.ssimmie.todos.performance.benchmark;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class RootResourceAction {

    public static ChainBuilder queryRoot() {
        return exec(http("Query API root")
                .get("/")
                .header("Content-Type", "application/json")
                .header("Accept", "application/hal+json")
                .check(status().is(200))
                .check(jsonPath("$._links.self.href").is("http://localhost:8181/"))
                .check(jsonPath("$._links.checklists.href").saveAs("checklistsUrl"))
        );
    }
}