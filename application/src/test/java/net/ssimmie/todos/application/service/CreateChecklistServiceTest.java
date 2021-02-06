package net.ssimmie.todos.application.service;

import static net.ssimmie.todos.application.port.in.CreateChecklistCommand.newCreateChecklistCommand;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class CreateChecklistServiceTest {

  @Test
  public void shouldCreateNewNamedChecklist() {
    final var expectedChecklistName = "checklist";
    final var createChecklistService = new CreateChecklistService(Mono::just);
    final var validCommand = newCreateChecklistCommand(expectedChecklistName);

    StepVerifier.create(createChecklistService.createChecklist(validCommand))
        .assertNext(c -> assertThat(c.getName().getValue()).isEqualTo(expectedChecklistName))
        .verifyComplete();
  }
}
