package net.ssimmie.todos.application.port.in;

import net.ssimmie.todos.domain.Checklist;
import reactor.core.publisher.Mono;

public interface CreateChecklistUseCase {

  Mono<Checklist> createChecklist(final CreateChecklistCommand createChecklistCommand);
}
