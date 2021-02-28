package net.ssimmie.todos.domain;

import static java.util.UUID.randomUUID;
import static net.ssimmie.todos.domain.ChecklistId.newChecklistId;
import static org.assertj.core.api.Assertions.assertThat;

import com.jparams.verifier.tostring.ToStringVerifier;
import java.util.UUID;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class ChecklistIdTest {

  @Test
  public void shouldBeValueType() {
    final UUID uuid = randomUUID();
    final UUID differentUuid = randomUUID();

    final ChecklistId anId = newChecklistId(uuid);
    final ChecklistId sameId = newChecklistId(uuid);
    final ChecklistId differentId = newChecklistId(differentUuid);

    assertThat(anId).isEqualTo(sameId).isNotEqualTo(differentId);
  }

  @Test
  public void shouldRespectEqualsContract() {
    EqualsVerifier.forClass(ChecklistId.class).verify();
  }

  @Test
  public void shouldNotMutateValue() {
    final UUID expectedId = randomUUID();
    final ChecklistId checkListId = newChecklistId(expectedId);

    assertThat(checkListId.getValue()).isEqualTo(expectedId);
  }

  @Test
  public void shouldSupportToString() {
    ToStringVerifier.forClass(ChecklistId.class).verify();
  }
}
