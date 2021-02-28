package net.ssimmie.todos.application.adapter.in.web;

import static net.ssimmie.todos.application.adapter.in.web.Checklist.toChecklistResourceRepresentation;
import static net.ssimmie.todos.application.port.in.CreateChecklistCommand.newCreateChecklistCommand;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

import java.util.function.Function;
import net.ssimmie.todos.application.port.in.CreateChecklistUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/checklists")
public class ChecklistsResource {

  private final CreateChecklistUseCase createChecklistUseCase;

  @Autowired
  public ChecklistsResource(final CreateChecklistUseCase createChecklistUseCase) {
    this.createChecklistUseCase = createChecklistUseCase;
  }

  @GetMapping
  Mono<RepresentationModel<?>> get() {
    return linkTo(methodOn(ChecklistsResource.class).get())
        .withSelfRel()
        .toMono()
        .map(RepresentationModel::new);
  }

  @PostMapping
  Mono<ResponseEntity<EntityModel<Checklist>>> create(@RequestBody final Checklist checklist) {
    return Mono.fromSupplier(() -> newCreateChecklistCommand(checklist.getName()))
        .flatMap(createChecklistUseCase::createChecklist)
        .flatMap(
            c ->
                linkTo(methodOn(ChecklistResource.class).get(c.getId().orElseThrow().getValue()))
                    .withSelfRel()
                    .toMono()
                    .flatMap(toEntityModel(c)))
        .map(toCreated());
  }

  private Function<Link, Mono<EntityModel<Checklist>>> toEntityModel(
      final net.ssimmie.todos.domain.Checklist checklist) {
    return link -> Mono.just(EntityModel.of(toChecklistResourceRepresentation(checklist), link));
  }

  private Function<EntityModel<Checklist>, ResponseEntity<EntityModel<Checklist>>> toCreated() {
    return e -> ResponseEntity.created(e.getLink("self").orElseThrow().toUri()).body(e);
  }
}
