package net.ssimmie.todos.application.adapter.in.web;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

import java.util.UUID;
import net.ssimmie.todos.application.port.in.GetChecklistQuery;
import net.ssimmie.todos.domain.ChecklistId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ChecklistResource {

  private final GetChecklistQuery getChecklistQuery;

  @Autowired
  public ChecklistResource(final GetChecklistQuery getChecklistQuery) {
    this.getChecklistQuery = getChecklistQuery;
  }

  @GetMapping("/checklists/{checklistId}")
  Mono<ResponseEntity<EntityModel<Checklist>>> get(@PathVariable final UUID checklistId) {
    return linkTo(methodOn(ChecklistResource.class).get(checklistId))
        .withSelfRel()
        .toMono()
        .flatMap(
            link ->
                getChecklistQuery
                    .getChecklist(ChecklistId.newChecklistId(checklistId))
                    .map(Checklist::toChecklistResourceRepresentation)
                    .map(c -> EntityModel.of(c, link)))
        .map(ResponseEntity::ok);
  }
}
