package net.ssimmie.todos.application.port.out;

import net.ssimmie.todos.domain.Checklist;
import net.ssimmie.todos.domain.ChecklistId;
import reactor.core.publisher.Mono;

public interface LoadChecklistPort {

  Mono<Checklist> loadCheckList(final ChecklistId checklistId);
}
