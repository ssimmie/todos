package net.ssimmie.todos;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

final class SpringHateoasAssertions<T> {

  private final RepresentationModel<? super T> representationModel;

  private SpringHateoasAssertions(final RepresentationModel<? super T> representationModel) {
    this.representationModel = representationModel;
  }

  static <T> SpringHateoasAssertions<T> assertThat(
      final RepresentationModel<? super T> representationModel) {
    return new SpringHateoasAssertions<T>(representationModel);
  }

  void hasLink(final String rel, final String href) {
    Optional<Link> optionalLink = requireNonNull(representationModel).getLink(rel);
    optionalLink.ifPresentOrElse(link -> assertEquals(href, link.getHref()), Assertions::fail);
  }
}
