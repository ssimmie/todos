package net.ssimmie.todos.application.service;

import static net.ssimmie.todos.domain.Checklist.knownNamedEmptyChecklist;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import net.ssimmie.todos.domain.ChecklistId;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class GetChecklistServiceTest {

  @Test
  public void shouldRetrieveExistingChecklist() {
    final var expectedChecklistId = ChecklistId.newChecklistId(UUID.randomUUID());
    final var getChecklistService =
        new GetChecklistService(id -> Mono.just(knownNamedEmptyChecklist(id.getValue(), "")));

    StepVerifier.create(getChecklistService.getChecklist(expectedChecklistId))
        .assertNext(c -> assertThat(c.getId()).hasValue(expectedChecklistId))
        .verifyComplete();
  }
}
