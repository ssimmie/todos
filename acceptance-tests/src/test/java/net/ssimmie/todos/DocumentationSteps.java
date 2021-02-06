package net.ssimmie.todos;

import static net.ssimmie.todos.BaseSteps.serverUri;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class DocumentationSteps {

  private Document apiDoc;

  @When("the api client requests the application's documentation site")
  public void theApiClientRequestsTheApplicationSDocumentationSite() throws IOException {
    this.apiDoc = Jsoup.connect(serverUri.resolve("/docs/index.html").toURL().toString()).get();
  }

  @Then("the api documentation content is returned")
  public void theApiDocumentationContentIsReturned() throws IOException {
    assertEquals("Todos Application API Documentation", apiDoc.title());

    final var apiDocBody = apiDoc.body();
    assertRootResourceDocumented(apiDocBody);
    assertChecklistsResourceDocumented(apiDocBody);
    assertTaskResourceDocumented(apiDocBody);
  }

  private void assertRootResourceDocumented(final Element apiDocBody) {
    assertEquals(
        "{\"_links\":{\"self\":{\"href\":\"http://localhost:8080/\"},\"checklists\":{\"href\":\"http://localhost:8080/checklists\"},\"tasks\":{\"href\":\"http://localhost:8080/tasks\"}}}",
        apiDocBody
            .select("#content > div:nth-child(5) > div > div:nth-child(7) > div > div > pre > code")
            .text());
  }

  private void assertChecklistsResourceDocumented(final Element apiDocBody) {
    assertEquals(
        "{\"_links\":{\"self\":{\"href\":\"http://localhost:8080/checklists\"}}}",
        apiDocBody
            .select("#content > div:nth-child(6) > div > div:nth-child(8) > div > div > pre > code")
            .text());
    assertEquals(
        "{\"name\":\"derp\",\"_links\":{\"self\":{\"href\":\"http://localhost:8080/checklists\"}}}",
        apiDocBody
            .select(
                "#content > div:nth-child(6) > div > div:nth-child(16) > div > div > pre > code")
            .text());
  }

  private void assertTaskResourceDocumented(final Element apiDocBody) {
    assertEquals(
        "{\"_links\":{\"self\":{\"href\":\"http://localhost:8080/tasks\"}}}",
        apiDocBody
            .select("#content > div:nth-child(7) > div > div:nth-child(8) > div > div > pre > code")
            .text());
    assertEquals(
        "{\"todo\":\"derp\",\"_links\":{\"self\":{\"href\":\"http://localhost:8080/tasks\"}}}",
        apiDocBody
            .select(
                "#content > div:nth-child(7) > div > div:nth-child(16) > div > div > pre > code")
            .text());
  }
}
