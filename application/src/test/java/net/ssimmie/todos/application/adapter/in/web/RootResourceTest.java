package net.ssimmie.todos.application.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class RootResourceTest {

  @Test
  public void shouldReturnSelfLink() {
    final RootResource rootResource = new RootResource();

    final Mono<RepresentationModel<?>> rootResourceRepresentationMono = rootResource.get();

    StepVerifier.create(rootResourceRepresentationMono)
        .assertNext(root -> assertTrue(root.hasLink("self")))
        .verifyComplete();
  }

  @Test
  public void shouldReturnTasksLink() {
    final RootResource rootResource = new RootResource();

    final Mono<RepresentationModel<?>> rootResourceRepresentationMono = new RootResource().get();

    StepVerifier.create(rootResourceRepresentationMono)
        .assertNext(
            root -> {
              assertTrue(root.hasLink("tasks"));
              assertThat(root.getLink("tasks")).map(Link::getHref).hasValue("/tasks");
            })
        .verifyComplete();
  }
}
