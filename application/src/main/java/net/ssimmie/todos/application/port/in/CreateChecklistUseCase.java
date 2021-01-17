package net.ssimmie.todos.application.port.in;

import net.ssimmie.todos.domain.Checklist;

public interface CreateChecklistUseCase {

  Checklist createChecklist(final CreateChecklistCommand createChecklistCommand);
}
