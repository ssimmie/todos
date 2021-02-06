package net.ssimmie.todos.domain;

import static net.ssimmie.todos.domain.Checklist.knownNamedEmptyChecklist;
import static net.ssimmie.todos.domain.Checklist.namedEmptyChecklist;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class ChecklistTest {

  @Test
  public void shouldCreateNamedEmptyChecklistWithUniqueId() {
    final Checklist jobs = namedEmptyChecklist("jobs");

    assertThat(jobs.getId()).isNotEmpty();
  }

  @Test
  public void shouldCreateNamedEmptyChecklistWithNoTodos() {
    final Checklist jobs = namedEmptyChecklist("jobs");

    assertThat(jobs.getTodos()).isEmpty();
  }

  @Test
  public void shouldCreateNamedEmptyChecklist() {
    final String expectedName = "jobs";
    final Checklist jobs = namedEmptyChecklist(expectedName);

    assertThat(jobs.getName().getValue()).isEqualTo(expectedName);
  }

  @Test
  public void shouldCreateKnownNamedEmptyChecklist() {
    final UUID expectedId = UUID.randomUUID();
    final String expectedName = "jobs";
    final Checklist jobs = knownNamedEmptyChecklist(expectedId, expectedName);

    assertThat(jobs.getId()).map(ChecklistId::getValue).hasValue(expectedId);
    assertThat(jobs.getName().getValue()).isEqualTo(expectedName);
  }
}
