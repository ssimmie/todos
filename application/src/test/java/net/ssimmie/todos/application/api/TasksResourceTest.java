package net.ssimmie.todos.application.api;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.CREATED;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class TasksResourceTest {

  @Test
  public void shouldReturnSelfLink() {
    final TasksResource tasksResource = new TasksResource();

    final Mono<RepresentationModel<?>> tasksResourceRepresentationMono = tasksResource.get();

    StepVerifier.create(tasksResourceRepresentationMono)
        .assertNext(
            tasks -> {
              assertTrue(tasks.hasLink("self"));
              assertThat(tasks.getLink("self")).map(Link::getHref).hasValue("/tasks");
            })
        .verifyComplete();
  }

  @Test
  public void shouldCreateTask() {
    final String expectedTodo = "derp";
    final TasksResource tasksResource = new TasksResource();

    final Task thing = new Task();
    thing.setTodo(expectedTodo);
    final Mono<ResponseEntity<EntityModel<Task>>> responseEntityMono = tasksResource.create(thing);

    StepVerifier.create(responseEntityMono)
        .assertNext(
            r -> {
              assertThat(r.getStatusCode()).isEqualTo(CREATED);
              assertThat(r.getHeaders().getLocation()).hasPath("/tasks");

              final Task actualTask = requireNonNull(r.getBody()).getContent();
              assertThat(requireNonNull(actualTask).getTodo()).isEqualTo(expectedTodo);
            })
        .verifyComplete();
  }
}
