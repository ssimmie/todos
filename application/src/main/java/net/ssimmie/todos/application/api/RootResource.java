package net.ssimmie.todos.application.api;

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
    final Class<RootResource> rootResource = RootResource.class;
    final Class<TasksResource> tasksResource = TasksResource.class;

    final Mono<Link> selfLink = linkTo(methodOn(rootResource).get())
        .withSelfRel()
        .toMono();

    final Mono<Link> tasksLink = linkTo(methodOn(tasksResource).get())
        .withRel("tasks")
        .toMono();

    return Flux.concat(selfLink, tasksLink).collectList().map(RepresentationModel::new);
  }
}
