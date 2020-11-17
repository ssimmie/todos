package net.ssimmie.todos;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

public class RootResourceRepresentation extends RepresentationModel<RootResourceRepresentation> {

  public RootResourceRepresentation() {
    super();
  }

  public RootResourceRepresentation(final Link initialLink) {
    super(initialLink);
  }

  public RootResourceRepresentation(final Iterable<Link> initialLinks) {
    super(initialLinks);
  }
}
