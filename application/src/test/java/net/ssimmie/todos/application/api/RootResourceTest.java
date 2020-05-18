package net.ssimmie.todos.application.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.RepresentationModel;
import reactor.core.publisher.Mono;

public class RootResourceTest {

  @Test
  public void should() {
    final RootResource rootResource = new RootResource();

    final Mono<RepresentationModel<?>> mono = rootResource.get();
    assertNotNull(mono);
  }
}
