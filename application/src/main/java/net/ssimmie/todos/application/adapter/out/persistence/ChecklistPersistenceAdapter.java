package net.ssimmie.todos.application.adapter.out.persistence;

import static com.datastax.oss.driver.api.core.uuid.Uuids.timeBased;
import static net.ssimmie.todos.domain.Checklist.knownNamedEmptyChecklist;

import net.ssimmie.todos.application.port.out.CreateChecklistPort;
import net.ssimmie.todos.application.port.out.LoadChecklistPort;
import net.ssimmie.todos.domain.Checklist;
import net.ssimmie.todos.domain.ChecklistId;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ChecklistPersistenceAdapter implements CreateChecklistPort, LoadChecklistPort {

  private final ReactiveChecklistRepository reactiveChecklistRepository;

  public ChecklistPersistenceAdapter(
      final ReactiveChecklistRepository reactiveChecklistRepository) {
    this.reactiveChecklistRepository = reactiveChecklistRepository;
  }

  @Override
  public Mono<Checklist> insertChecklist(final Checklist checklist) {
    return reactiveChecklistRepository
        .save(
            new net.ssimmie.todos.application.adapter.out.persistence.Checklist(
                timeBased(), checklist.getName().getValue()))
        .map(r -> knownNamedEmptyChecklist(r.getId(), r.getName()))
        .log();
  }

  @Override
  public Mono<Checklist> loadCheckList(final ChecklistId checklistId) {
    return reactiveChecklistRepository
        .findById(checklistId.getValue())
        .map(r -> knownNamedEmptyChecklist(r.getId(), r.getName()))
        .log();
  }
}
