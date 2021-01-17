package net.ssimmie.todos.application.adapter.in.web;

import static net.ssimmie.todos.application.adapter.in.web.Checklist.toChecklistResourceRepresentation;
import static net.ssimmie.todos.domain.Checklist.namedEmptyChecklist;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ChecklistTest {

  @Test
  public void shouldContainName() {
    final String expectedName = "todos";
    final Checklist checklist = new Checklist();
    checklist.setName(expectedName);

    assertThat(checklist.getName()).isEqualTo(expectedName);
  }

  @Test
  public void shouldCreateFromDomainObject() {
    final String expectedName = "simple";
    final Checklist checklist =
        toChecklistResourceRepresentation(namedEmptyChecklist(expectedName));

    assertThat(checklist).isNotNull().extracting("name").isEqualTo(expectedName);
  }
}
