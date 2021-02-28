package net.ssimmie.todos.application.adapter.out.persistence;

import static net.ssimmie.todos.domain.Checklist.namedEmptyChecklist;
import static net.ssimmie.todos.domain.ChecklistId.newChecklistId;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
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

  @Test
  public void shouldFindExistingChecklist() {
    final var expectedUuid = UUID.randomUUID();
    final var expectedChecklistId = newChecklistId(expectedUuid);
    final var stubChecklistRepository = new StubChecklistRepository();
    stubChecklistRepository.insert(new Checklist(expectedUuid, "test"));
    final var checklistPersistenceAdapter =
        new ChecklistPersistenceAdapter(stubChecklistRepository);

    StepVerifier.create(checklistPersistenceAdapter.loadCheckList(expectedChecklistId))
        .assertNext(c -> assertThat(c.getId()).contains(expectedChecklistId))
        .verifyComplete();
  }
}
