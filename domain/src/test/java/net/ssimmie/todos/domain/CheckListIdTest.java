package net.ssimmie.todos.domain;

import static java.util.UUID.randomUUID;
import static net.ssimmie.todos.domain.CheckListId.newCheckListId;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class CheckListIdTest {

  @Test
  public void shouldBeValueType() {
    final UUID uuid = randomUUID();
    final UUID differentUuid = randomUUID();

    final CheckListId anId = newCheckListId(uuid);
    final CheckListId sameId = newCheckListId(uuid);
    final CheckListId differentId = newCheckListId(differentUuid);

    assertThat(anId).isEqualTo(sameId).isNotEqualTo(differentId);
  }

  @Test
  public void shouldRespectEqualsContract() {
    EqualsVerifier.forClass(CheckListId.class).verify();
  }

  @Test
  public void shouldNotMutateValue() {
    final UUID expectedId = randomUUID();
    final CheckListId checkListId = CheckListId.newCheckListId(expectedId);

    assertThat(checkListId.getValue()).isEqualTo(expectedId);
  }
}
