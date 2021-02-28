package net.ssimmie.todos.application.adapter.in.web;

import static java.util.Objects.requireNonNull;
import static net.ssimmie.todos.domain.Checklist.knownNamedEmptyChecklist;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

import java.net.URI;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ChecklistResourceTest {

  @Test
  public void shouldReturnExistentChecklist() {
    final var expectedId = UUID.randomUUID();
    final var expectedUri = URI.create(String.format("/checklists/%s", expectedId.toString()));
    final var checklistResource =
        new ChecklistResource(id -> Mono.just(knownNamedEmptyChecklist(id.getValue(), "name")));

    final var response = checklistResource.get(expectedId);

    StepVerifier.create(response)
        .assertNext(
            r -> {
              assertThat(r.getStatusCode()).isEqualTo(OK);
              assertThat(requireNonNull(r.getBody()).getLink("self"))
                  .map(Link::getHref)
                  .hasValue(expectedUri.toString());
            })
        .verifyComplete();
  }
}
