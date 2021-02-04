package net.ssimmie.todos.application.port.out;

import net.ssimmie.todos.domain.Checklist;
import reactor.core.publisher.Mono;

public interface CreateChecklistPort {

  Mono<Checklist> insertChecklist(final Checklist checklist);
}
