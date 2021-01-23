package net.ssimmie.todos.application.service;

import static net.ssimmie.todos.application.port.in.CreateChecklistCommand.newCreateChecklistCommand;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CreateChecklistServiceTest {

  @Test
  public void shouldCreateNewNamedChecklist() {
    final var expectedChecklistName = "checklist";
    final var createChecklistService = new CreateChecklistService(checklist -> checklist);
    final var validCommand = newCreateChecklistCommand(expectedChecklistName);

    assertThat(createChecklistService.createChecklist(validCommand).getName().getValue())
        .isEqualTo(expectedChecklistName);
  }
}
