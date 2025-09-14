package net.ssimmie.todos.application.adapter.in.web;

import static net.ssimmie.todos.domain.Checklist.knownNamedEmptyChecklist;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;

import java.util.UUID;
import net.ssimmie.todos.application.port.in.GetChecklistQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = ChecklistResource.class)
public class ChecklistResourceIT {

  @Autowired private WebTestClient webTestClient;

  @MockitoBean private GetChecklistQuery getChecklistQuery;

  @Test
  public void shouldRetrieveChecklist() {
    final var expectedChecklistId = UUID.randomUUID();
    final var expectedUri = String.format("/checklists/%s", expectedChecklistId);
    final var expectedName = "name";
    when(getChecklistQuery.getChecklist(any()))
        .thenReturn(Mono.just(knownNamedEmptyChecklist(expectedChecklistId, expectedName)));

    this.webTestClient
        .get()
        .uri(expectedUri)
        .accept(HAL_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$._links.self.href")
        .isEqualTo(expectedUri)
        .jsonPath("$.name")
        .isEqualTo(expectedName);
  }
}
