package net.ssimmie.todos;

import static java.util.Objects.requireNonNull;
import static net.ssimmie.todos.BaseSteps.restClient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.OK;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;

public class ApiSteps {

  private ResponseEntity<RepresentationModel> rootResourceRepresentation;

  @When("the api client queries the application's root resource")
  public void anApiClientQueriesTheApplicationSRootResource() {
    rootResourceRepresentation = restClient.getForEntity("/", RepresentationModel.class);
  }

  @Then("the services available resources should be returned")
  public void theServicesAvailableResourcesShouldBeReturned() {
    assertEquals(OK, rootResourceRepresentation.getStatusCode());
    assertTrue(requireNonNull(rootResourceRepresentation.getBody()).hasLink("self"));
  }
}
