package net.ssimmie.todos.application.api;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
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
}
