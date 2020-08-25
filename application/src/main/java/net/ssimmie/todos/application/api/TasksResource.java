package net.ssimmie.todos.application.api;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TasksResource {

  @GetMapping
  Mono<RepresentationModel<?>> get() {
    final Class<TasksResource> tasksResourceClass = TasksResource.class;

    return linkTo(methodOn(tasksResourceClass).get())
        .withSelfRel()
        .toMono()
        .map(RepresentationModel::new);
  }
}
