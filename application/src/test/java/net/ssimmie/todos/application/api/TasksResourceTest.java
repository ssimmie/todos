package net.ssimmie.todos.application.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
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
}
