package net.ssimmie.todos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

public class TasksResourceRepresentation extends RepresentationModel<TasksResourceRepresentation> {

  public TasksResourceRepresentation() {
    super();
  }

  @JsonProperty("_links")
  public void setLinks(final Map<String, Link> links) {
    links.forEach((label, link) -> add(link.withRel(label)));
  }

  public TasksResourceRepresentation(final Link initialLink) {
    super(initialLink);
  }

  public TasksResourceRepresentation(final Iterable<Link> initialLinks) {
    super(initialLinks);
  }
}
