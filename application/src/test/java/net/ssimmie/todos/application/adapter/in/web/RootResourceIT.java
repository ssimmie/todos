package net.ssimmie.todos.application.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = RootResource.class)
public class RootResourceIT {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  public void shouldProvideEntryPointToAvailableTopLevelResources() {
    this.webTestClient
        .get()
        .uri("/")
        .accept(MediaTypes.HAL_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$._links.self.href")
        .isEqualTo("/")
        .jsonPath("$._links.checklists.href")
        .isEqualTo("/checklists")
        .jsonPath("$._links.tasks.href")
        .isEqualTo("/tasks");
  }
}
