package net.ssimmie.todos.application.adapter.in.web;

import static net.ssimmie.todos.domain.Checklist.namedEmptyChecklist;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import net.ssimmie.todos.application.port.in.CreateChecklistUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = ChecklistsResource.class)
public class ChecklistsResourceIT {

  @Autowired private WebTestClient webTestClient;

  @MockBean private CreateChecklistUseCase createChecklistUseCase;

  @Test
  public void shouldListAllChecklists() {
    this.webTestClient
        .get()
        .uri("/checklists")
        .accept(HAL_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$._links.self.href")
        .isEqualTo("/checklists");
  }

  @Test
  public void shouldCreateChecklists() {
    final Checklist expectedChecklist = new Checklist();
    expectedChecklist.setName("derp");
    when(createChecklistUseCase.createChecklist(any()))
        .thenReturn(Mono.just(namedEmptyChecklist(expectedChecklist.getName())));

    this.webTestClient
        .post()
        .uri("/checklists")
        .contentType(APPLICATION_JSON)
        .accept(HAL_JSON)
        .bodyValue(expectedChecklist)
        .exchange()
        .expectBody()
        .jsonPath("$.name")
        .isEqualTo("derp")
        .jsonPath("$._links.self.href")
        .isEqualTo("/checklists");
  }
}
