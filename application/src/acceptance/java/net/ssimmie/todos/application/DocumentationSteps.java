package net.ssimmie.todos.application;

import static net.ssimmie.todos.application.StepDefinitions.applicationContext;
import static net.ssimmie.todos.application.StepDefinitions.restClient;
import static net.ssimmie.todos.application.StepDefinitions.serverUri;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;

public class DocumentationSteps {

  @When("an api client requests the application's documentation site")
  public void anApiClientRequestsTheApplicationSDocumentationSite() {
    restClient.getForEntity("/docs/index.html", String.class);
  }

  @Then("the api documentation content is returned")
  public void theApiDocumentationContentIsReturned() throws IOException {
    try (final WebClient webClient = new WebClient()) {
      final HtmlPage page = webClient.getPage(serverUri.resolve("/docs/index.html").toURL());
      assertEquals("Todos Application API Documentation", page.getTitleText());

      final String pageAsXml = page.asXml();
      assertTrue(pageAsXml.contains("Todos Healthcheck Resource"));
      assertTrue(pageAsXml.contains("Todos Root Resource"));
    } finally {
      applicationContext.close();
    }
  }
}
