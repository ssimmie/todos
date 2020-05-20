package net.ssimmie.todos.application;

import static java.lang.Integer.parseInt;
import static java.net.URI.create;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.SpringApplication.run;
import static org.springframework.http.HttpStatus.OK;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

public class StepDefinitions {

  private ConfigurableApplicationContext applicationContext;
  private ResponseEntity<String> healthcheckResourceRepresentation;
  private ResponseEntity<RepresentationModel> rootResourceRepresentation;
  private RestOperations restClient;
  private URI serverUri;

  @Given("a running instance of the application")
  public void a_running_instance_of_the_application() throws MalformedURLException {
    this.applicationContext = run(TodosApplication.class, "--server.port=0");
    this.serverUri = create("http://localhost:" + parseInt(
        requireNonNull(applicationContext.getEnvironment().getProperty("local.server.port"))));
    this.restClient = new RestTemplateBuilder().rootUri(serverUri.toString()).build();
  }

  @When("an api client queries the application's actuator healthcheck resource")
  public void an_api_client_queries_the_application_s_actuator_healthcheck_resource() {
    healthcheckResourceRepresentation = restClient.getForEntity("/actuator/health", String.class);
  }

  @When("an api client queries the application's root resource")
  public void anApiClientQueriesTheApplicationSRootResource() {
    rootResourceRepresentation = restClient.getForEntity("/", RepresentationModel.class);
  }

  @When("an api client requests the application's documentation site")
  public void anApiClientRequestsTheApplicationSDocumentationSite() {
    restClient.getForEntity("/docs/index.html", String.class);
  }

  @Then("status should be UP in the healthcheck resource representation")
  public void status_should_be_UP_in_the_healthcheck_resource_representation() {
    try {
      assertEquals(OK, healthcheckResourceRepresentation.getStatusCode());
      assertEquals("{\"status\":\"UP\"}", healthcheckResourceRepresentation.getBody());
    } finally {
      this.applicationContext.close();
    }
  }

  @Then("the services available resources should be returned")
  public void theServicesAvailableResourcesShouldBeReturned() {
    try {
      assertEquals(OK, rootResourceRepresentation.getStatusCode());
      assertTrue(requireNonNull(rootResourceRepresentation.getBody()).hasLink("self"));
    } finally {
      this.applicationContext.close();
    }
  }

  @Then("the api documentation content is returned")
  public void theApiDocumentationContentIsReturned() throws IOException {
    try (final WebClient webClient = new WebClient()) {
      final HtmlPage page = webClient.getPage(serverUri.resolve("/docs/index.html").toURL());
      assertEquals("Todos Application API Documentation", page.getTitleText());

      final String pageAsXml = page.asXml();
      assertTrue(pageAsXml.contains("Todos Healthcheck Resource"));
      assertTrue(pageAsXml.contains("Todos Root Resource"));
    }
  }
}
