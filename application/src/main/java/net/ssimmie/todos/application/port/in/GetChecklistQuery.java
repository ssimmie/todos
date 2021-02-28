package net.ssimmie.todos.application.port.in;

import net.ssimmie.todos.domain.Checklist;
import net.ssimmie.todos.domain.ChecklistId;
import reactor.core.publisher.Mono;

public interface GetChecklistQuery {

  Mono<Checklist> getChecklist(final ChecklistId checklistId);
}
