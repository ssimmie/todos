package net.ssimmie.todos.application.adapter.out.persistence;

import net.ssimmie.todos.application.port.out.CreateChecklistPort;
import net.ssimmie.todos.domain.Checklist;
import org.springframework.stereotype.Component;

@Component
public class ChecklistPersistenceAdapter implements CreateChecklistPort {

  @Override
  public Checklist insertChecklist(final Checklist checklist) {
    return checklist;
  }
}
