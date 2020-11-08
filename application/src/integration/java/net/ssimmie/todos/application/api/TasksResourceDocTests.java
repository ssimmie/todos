package net.ssimmie.todos.application.api;

import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.reactive.server.WebTestClient.bindToApplicationContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@Tag("docs")
@WebFluxTest(controllers = TasksResource.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class TasksResourceDocTests {

  private WebTestClient webTestClient;

  @BeforeEach
  public void setUp(
      final ApplicationContext applicationContext,
      final RestDocumentationContextProvider restDocumentation) {
    this.webTestClient =
        bindToApplicationContext(applicationContext)
            .configureClient()
            .filter(documentationConfiguration(restDocumentation))
            .build();
  }

  @Test
  public void shouldListAllTasks(
      @Autowired final WebTestClient webClient) {
    webTestClient
        .get()
        .uri("/tasks")
        .accept(HAL_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$._links.self.href")
        .isEqualTo("http://localhost:8080/tasks")
        .consumeWith(document("tasks"));
  }

  @Test
  public void shouldCreateTask(
      @Autowired final WebTestClient webClient) {
    final Task expectedTask = new Task();
    expectedTask.setTodo("derp");
    webTestClient
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
        .isEqualTo("http://localhost:8080/tasks")
        .consumeWith(document("tasks"));
  }
}
