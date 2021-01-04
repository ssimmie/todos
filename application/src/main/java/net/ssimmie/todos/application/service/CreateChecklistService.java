package net.ssimmie.todos.application.service;

import static net.ssimmie.todos.domain.Checklist.namedEmptyChecklist;

import net.ssimmie.todos.application.port.in.CreateChecklistCommand;
import net.ssimmie.todos.application.port.in.CreateChecklistUseCase;
import net.ssimmie.todos.domain.Checklist;
import org.springframework.stereotype.Service;

@Service
public class CreateChecklistService implements CreateChecklistUseCase {

  @Override
  public Checklist createChecklist(final CreateChecklistCommand createChecklistCommand) {
    return namedEmptyChecklist(createChecklistCommand.getChecklistName());
  }
}
