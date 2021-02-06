package net.ssimmie.todos.application.adapter.out.persistence;

import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;
import static net.ssimmie.todos.application.adapter.out.persistence.Checklist.checklistEntityFromDomain;
import static net.ssimmie.todos.domain.Checklist.knownNamedEmptyChecklist;
import static net.ssimmie.todos.domain.Checklist.namedEmptyChecklist;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ChecklistTest {

  @Test
  public void shouldCreateEntityFromUnidentifiedDomainType() {
    final var expectedName = "test";
    final var checklist = checklistEntityFromDomain(namedEmptyChecklist(expectedName));

    assertThat(checklist.getId()).isNotNull();
    assertThat(checklist.getName()).isEqualTo(expectedName);
  }

  @Test
  public void shouldCreateEntityFromIdentifiedDomainType() {
    final var expectedId = randomUUID();
    final var expectedName = "test";
    final var checklist =
        checklistEntityFromDomain(knownNamedEmptyChecklist(expectedId, expectedName));

    assertThat(checklist.getId()).isEqualTo(expectedId);
    assertThat(checklist.getName()).isEqualTo(expectedName);
  }

  @Test
  public void shouldIncludeAllFieldsInToString() {
    final var expectedToString = "Checklist{id=e0466611-f67f-4918-bc18-11cf4f579bc4, name='test'}";
    final var checklist = new Checklist(fromString("e0466611-f67f-4918-bc18-11cf4f579bc4"), "test");

    assertThat(checklist.toString()).isEqualTo(expectedToString);
  }
}
