package net.ssimmie.todos.application.port.in;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;

public class CreateChecklistCommand {

  private static final Validator VALIDATOR;

  static {
    VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @NotBlank(message = "Name is mandatory")
  private final String checklistName;

  private CreateChecklistCommand(final String checklistName) {
    this.checklistName = checklistName;
    final var violations = VALIDATOR.validate(this);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  public static CreateChecklistCommand newCreateChecklistCommand(final String checklistName) {
    return new CreateChecklistCommand(checklistName);
  }

  public String getChecklistName() {
    return checklistName;
  }
}
