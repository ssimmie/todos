package net.ssimmie.todos.acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.SpringApplication.run;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Objects;
import net.ssimmie.todos.TodosApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class StepDefinitions {

  private ConfigurableApplicationContext applicationContext;
  private ResponseEntity<String> healthcheckResourceRepresentation;

  @Given("a running instance of the application")
  public void a_running_instance_of_the_application() {
    this.applicationContext = run(TodosApplication.class, "--server.port=0");
  }

  @When("an api client queries the application's actuator healthcheck resource")
  public void an_api_client_queries_the_application_s_actuator_healthcheck_resource() {
    final int port =
        Integer.parseInt(
            Objects.requireNonNull(
                applicationContext.getEnvironment().getProperty("local.server.port")));

    healthcheckResourceRepresentation =
        new RestTemplate()
            .getForEntity("http://localhost:" + port + "/actuator/health", String.class);
  }

  @Then("status should be UP in the healthcheck resource representation")
  public void status_should_be_UP_in_the_healthcheck_resource_representation() {
    try {
      assertEquals("{\"status\":\"UP\"}", healthcheckResourceRepresentation.getBody());
    } finally {
      this.applicationContext.close();
    }
  }
}
