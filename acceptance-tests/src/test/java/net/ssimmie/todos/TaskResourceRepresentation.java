package net.ssimmie.todos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

public class TaskResourceRepresentation extends RepresentationModel<TaskResourceRepresentation> {

  private String todo;

  @JsonProperty("_links")
  public void setLinks(final Map<String, Link> links) {
    links.forEach((label, link) -> add(link.withRel(label)));
  }

  public String getTodo() {
    return todo;
  }

  public void setTodo(final String todo) {
    this.todo = todo;
  }
}
