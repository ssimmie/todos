package net.ssimmie.todos;

import org.springframework.hateoas.RepresentationModel;

public class ChecklistResourceRepresentation
    extends RepresentationModel<ChecklistResourceRepresentation> {
  private String name;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }
}
