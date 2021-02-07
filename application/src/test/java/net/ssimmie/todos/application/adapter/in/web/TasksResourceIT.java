package net.ssimmie.todos.application.adapter.in.web;

import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = TasksResource.class)
public class TasksResourceIT {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  public void shouldListAllTasks() {
    this.webTestClient
        .get()
        .uri("/tasks")
        .accept(HAL_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$._links.self.href")
        .isEqualTo("/tasks");
  }

  @Test
  public void shouldCreateTask() {
    final Task expectedTask = new Task();
    expectedTask.setTodo("derp");
    this.webTestClient
        .post()
        .uri("/tasks")
        .contentType(APPLICATION_JSON)
        .accept(HAL_JSON)
        .bodyValue(expectedTask)
        .exchange()
        .expectBody()
        .jsonPath("$.todo")
        .isEqualTo("derp")
        .jsonPath("$._links.self.href")
        .isEqualTo("/tasks");
  }
}
