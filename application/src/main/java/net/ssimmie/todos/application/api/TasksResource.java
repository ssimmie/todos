package net.ssimmie.todos.application.api;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;
import static org.springframework.http.ResponseEntity.created;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TasksResource {

  private static final Logger logger = LoggerFactory.getLogger(TasksResource.class);

  @GetMapping
  Mono<RepresentationModel<?>> get() {
    final Class<TasksResource> tasksResourceClass = TasksResource.class;

    return linkTo(methodOn(tasksResourceClass).get())
        .withSelfRel()
        .toMono()
        .map(RepresentationModel::new);
  }

  @PostMapping
  Mono<ResponseEntity<EntityModel<Task>>> create(@RequestBody final Task task) {
    logger.info("Creating task");

    return linkTo(methodOn(TasksResource.class).get())
        .withSelfRel()
        .toMono()
        .map(link -> EntityModel.of(task, link))
        .map(
            taskEntityModel ->
                created(taskEntityModel.getLink("self").orElseThrow().toUri())
                    .body(taskEntityModel));
  }
}
