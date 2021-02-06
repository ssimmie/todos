package net.ssimmie.todos.application.adapter.out.persistence;

import static net.ssimmie.todos.domain.Checklist.namedEmptyChecklist;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class ChecklistPersistenceAdapterTest {

  @Test
  public void shouldInsertNewChecklist() {
    final var expectedChecklistName = "test";
    final var expectedChecklist = namedEmptyChecklist(expectedChecklistName);
    final var checklistPersistenceAdapter =
        new ChecklistPersistenceAdapter(new StubChecklistRepository());

    StepVerifier.create(checklistPersistenceAdapter.insertChecklist(expectedChecklist))
        .assertNext(c -> assertThat(c.getName().getValue()).isEqualTo(expectedChecklistName))
        .verifyComplete();
  }
}
