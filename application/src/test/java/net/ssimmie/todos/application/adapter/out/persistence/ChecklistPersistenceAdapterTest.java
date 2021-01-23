package net.ssimmie.todos.application.adapter.out.persistence;

import static net.ssimmie.todos.domain.Checklist.namedEmptyChecklist;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ChecklistPersistenceAdapterTest {

  @Test
  public void shouldInsertNewChecklist() {
    final var expectedChecklist = namedEmptyChecklist("test");
    final var checklistPersistenceAdapter = new ChecklistPersistenceAdapter();

    assertThat(checklistPersistenceAdapter.insertChecklist(expectedChecklist))
        .isEqualTo(expectedChecklist);
  }
}
