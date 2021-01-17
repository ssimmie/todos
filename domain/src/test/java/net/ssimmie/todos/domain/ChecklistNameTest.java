package net.ssimmie.todos.domain;

import static net.ssimmie.todos.domain.ChecklistName.newChecklistName;
import static org.assertj.core.api.Assertions.assertThat;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class ChecklistNameTest {

  @Test
  public void shouldBeValueType() {

    final ChecklistName name = newChecklistName("name");
    final ChecklistName sameName = newChecklistName("name");
    final ChecklistName differentName = newChecklistName("eman");

    assertThat(name).isEqualTo(sameName).isNotEqualTo(differentName);
  }

  @Test
  public void shouldRespectEqualsContract() {
    EqualsVerifier.forClass(ChecklistName.class).verify();
  }

  @Test
  public void shouldNotMutateValue() {
    final String expectedName = "name";
    final ChecklistName checklistName = newChecklistName(expectedName);

    assertThat(checklistName.getValue()).isEqualTo(expectedName);
  }
}
