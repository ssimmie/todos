package net.ssimmie.todos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CheckListNameTest {

  @Test
  public void shouldBeValueType() {

    final CheckListName name = CheckListName.newCheckListName("name");
    final CheckListName sameName = CheckListName.newCheckListName("name");
    final CheckListName differentName = CheckListName.newCheckListName("eman");

    assertThat(name).isEqualTo(sameName).isNotEqualTo(differentName);
  }

  @Test
  public void shouldRespectEqualsContract() {
    EqualsVerifier.forClass(CheckListName.class).verify();
  }

  @Test
  public void shouldNotMutateValue() {
    final String expectedName = "name";
    final CheckListName checkListName = CheckListName.newCheckListName(expectedName);

    Assertions.assertThat(checkListName.getValue()).isEqualTo(expectedName);
  }
}
