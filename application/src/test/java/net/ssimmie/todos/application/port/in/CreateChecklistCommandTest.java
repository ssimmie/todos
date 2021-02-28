package net.ssimmie.todos.application.port.in;

import static net.ssimmie.todos.application.port.in.CreateChecklistCommand.newCreateChecklistCommand;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jparams.verifier.tostring.ToStringVerifier;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

class CreateChecklistCommandTest {

  @Test
  public void shouldAcceptValidChecklistNames() {
    final String expectedName = "named";
    assertThat(newCreateChecklistCommand(expectedName))
        .isNotNull()
        .extracting("checklistName")
        .isEqualTo(expectedName);
  }

  @Test
  public void shouldRejectNullChecklistNames() {
    assertThatThrownBy(() -> newCreateChecklistCommand(null))
        .isInstanceOf(ConstraintViolationException.class)
        .hasMessage("checklistName: Name is mandatory");
  }

  @Test
  public void shouldRejectEmptyChecklistNames() {
    assertThatThrownBy(() -> newCreateChecklistCommand(""))
        .isInstanceOf(ConstraintViolationException.class)
        .hasMessage("checklistName: Name is mandatory");
  }

  @Test
  public void shouldRejectBlankChecklistNames() {
    assertThatThrownBy(() -> newCreateChecklistCommand("  "))
        .isInstanceOf(ConstraintViolationException.class)
        .hasMessage("checklistName: Name is mandatory");
  }

  @Test
  public void shouldOverrideToString() {
    ToStringVerifier.forClass(CreateChecklistCommand.class).verify();
  }
}
