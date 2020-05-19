package net.ssimmie.todos.application.api;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
class RootResource {

  @GetMapping
  Mono<RepresentationModel<?>> get() {
    BlockHound.install();
    final Class<RootResource> rootResource = RootResource.class;

    return linkTo(methodOn(rootResource).get())
        .withSelfRel()
        .toMono()
        .map(RepresentationModel::new);
  }
}
