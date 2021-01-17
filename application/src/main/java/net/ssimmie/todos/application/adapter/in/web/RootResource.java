package net.ssimmie.todos.application.adapter.in.web;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
class RootResource {

  @GetMapping
  Mono<RepresentationModel<?>> get() {
    return Flux.concat(selfLink(), checklistsLink(), tasksLink())
        .collectList()
        .map(RepresentationModel::new);
  }

  private Mono<Link> selfLink() {
    return linkTo(methodOn(RootResource.class).get()).withSelfRel().toMono();
  }

  private Mono<Link> checklistsLink() {
    return linkTo(methodOn(ChecklistsResource.class).get()).withRel("checklists").toMono();
  }

  private Mono<Link> tasksLink() {
    return linkTo(methodOn(TasksResource.class).get()).withRel("tasks").toMono();
  }
}
