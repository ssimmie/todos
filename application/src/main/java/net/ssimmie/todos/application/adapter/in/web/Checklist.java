package net.ssimmie.todos.application.adapter.in.web;

public class Checklist {

  private String name;

  public Checklist() {
    super();
  }

  static Checklist toChecklistResourceRepresentation(
      final net.ssimmie.todos.domain.Checklist checklist) {
    final Checklist resourceRepresentation = new Checklist();
    resourceRepresentation.setName(checklist.getName().getValue());
    return resourceRepresentation;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }
}
