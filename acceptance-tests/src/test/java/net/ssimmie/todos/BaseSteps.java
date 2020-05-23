package net.ssimmie.todos;

import static java.net.URI.create;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.net.MalformedURLException;
import java.net.URI;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

public class BaseSteps {

  static RestOperations restClient;
  static URI serverUri = create("http://localhost:8181");
  private ResponseEntity<String> healthcheckResourceRepresentation;

  @Given("an api client")
  public void an_api_client() throws MalformedURLException {
    restClient = new RestTemplateBuilder().rootUri(serverUri.toString()).build();
  }

  @When("the api client queries the application's actuator healthcheck resource")
  public void the_api_client_queries_the_application_s_actuator_healthcheck_resource() {
    healthcheckResourceRepresentation = restClient.getForEntity("/actuator/health", String.class);
  }

  @Then("status should be UP in the healthcheck resource representation")
  public void status_should_be_UP_in_the_healthcheck_resource_representation() {
    assertEquals(OK, healthcheckResourceRepresentation.getStatusCode());
    assertEquals("{\"status\":\"UP\"}", healthcheckResourceRepresentation.getBody());
  }
}
