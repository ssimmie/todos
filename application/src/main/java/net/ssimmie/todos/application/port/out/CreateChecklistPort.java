package net.ssimmie.todos.application.port.out;

import net.ssimmie.todos.domain.Checklist;

public interface CreateChecklistPort {

  Checklist insertChecklist(final Checklist checklist);
}
