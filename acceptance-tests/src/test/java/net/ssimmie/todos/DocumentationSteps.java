package net.ssimmie.todos;

import static net.ssimmie.todos.BaseSteps.serverUri;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;

public class DocumentationSteps {

  private HtmlPage page;

  @When("the api client requests the application's documentation site")
  public void theApiClientRequestsTheApplicationSDocumentationSite() throws IOException {
    try (final WebClient webClient = new WebClient()) {
      this.page = webClient.getPage(serverUri.resolve("/docs/index.html").toURL());
    }
  }

  @Then("the api documentation content is returned")
  public void theApiDocumentationContentIsReturned() throws IOException {
    assertEquals("Todos Application API Documentation", page.getTitleText());

    final String pageAsXml = page.asXml();
    assertTrue(pageAsXml.contains("Todos Healthcheck Resource"));
    assertTrue(pageAsXml.contains("{\"status\":\"UP\"}"));
    assertTrue(pageAsXml.contains("Todos Root Resource"));
    assertTrue(pageAsXml.contains("\"href\":\"http://localhost:8080/\""));
  }
}
