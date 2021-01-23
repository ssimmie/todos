package net.ssimmie.todos.application.service;

import static net.ssimmie.todos.application.port.in.CreateChecklistCommand.newCreateChecklistCommand;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import net.ssimmie.todos.application.port.out.CreateChecklistPort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateChecklistServiceTest {

  @Test
  public void shouldCreateNewNamedChecklist() {
    final CreateChecklistPort createChecklistPort = Mockito.mock(CreateChecklistPort.class);
    when(createChecklistPort.insertChecklist(any())).thenAnswer(i -> i.getArgument(0));
    final var createChecklistService = new CreateChecklistService(createChecklistPort);
    final var validCommand = newCreateChecklistCommand("checklist");

    assertThat(createChecklistService.createChecklist(newCreateChecklistCommand("checklist")))
        .isNotNull();
  }
}
