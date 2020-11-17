package net.ssimmie.todos;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

public class TasksResourceRepresentation extends RepresentationModel<TasksResourceRepresentation> {

  public TasksResourceRepresentation() {
    super();
  }

  public TasksResourceRepresentation(final Link initialLink) {
    super(initialLink);
  }

  public TasksResourceRepresentation(final Iterable<Link> initialLinks) {
    super(initialLinks);
  }
}
