package net.ssimmie.todos;

import static java.util.Objects.requireNonNull;
import static net.ssimmie.todos.BaseSteps.restClient;
import static net.ssimmie.todos.SpringHateoasAssertions.assertResource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.CREATED;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;

public class ApiSteps {

  private static final String EXPECTED_TODO = "acceptanceTest";
  private RepresentationModel<RootResourceRepresentation> rootResourceRepresentation;
  private ResponseEntity<TaskResourceRepresentation> newTask;

  @When("the api client queries the application's root resource")
  public void anApiClientQueriesTheApplicationSRootResource() {
    rootResourceRepresentation = restClient.getForObject("/", RootResourceRepresentation.class);
  }

  @And("the api client has discovered available resources")
  public void theApiClientHasDiscoveredAvailableResources() {
    anApiClientQueriesTheApplicationSRootResource();
  }

  @When("the api client creates a new task")
  public void theApiClientCreatesANewTask() {
    final TaskResourceRepresentation task = new TaskResourceRepresentation();
    task.setTodo(EXPECTED_TODO);
    this.newTask =
        restClient.postForEntity(
            rootResourceRepresentation.getLink("tasks").orElseThrow().getHref(),
            task,
            TaskResourceRepresentation.class);
  }

  @Then("the services available resources should be returned")
  public void theServicesAvailableResourcesShouldBeReturned() {
    assertResource(rootResourceRepresentation).hasLink("self", "http://localhost:8181/");
    assertResource(rootResourceRepresentation).hasLink("tasks", "http://localhost:8181/tasks");
  }

  @Then("the task will be maintained by the service")
  public void theTaskWillBeMaintainedByTheService() {
    assertEquals(CREATED, newTask.getStatusCode());
    assertResource(newTask.getBody()).hasLink("self", "http://localhost:8181/tasks");
    assertEquals(EXPECTED_TODO, requireNonNull(newTask.getBody()).getTodo());
  }
}
