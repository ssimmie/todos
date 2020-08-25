package net.ssimmie.todos;

import static net.ssimmie.todos.BaseSteps.restClient;
import static net.ssimmie.todos.SpringHateoasAssertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.hateoas.RepresentationModel;

public class ApiSteps {

  private RepresentationModel<RootResource> rootResourceRepresentation;

  @When("the api client queries the application's root resource")
  public void anApiClientQueriesTheApplicationSRootResource() {
    rootResourceRepresentation = restClient.getForObject("/", RootResource.class);
  }

  @Then("the services available resources should be returned")
  public void theServicesAvailableResourcesShouldBeReturned() {
    assertThat(rootResourceRepresentation).hasLink("self", "http://localhost:8181/");
    assertThat(rootResourceRepresentation).hasLink("tasks", "http://localhost:8181/tasks");
  }
}
