package net.ssimmie.todos;

import org.springframework.hateoas.RepresentationModel;

public class TaskResourceRepresentation extends RepresentationModel<TaskResourceRepresentation> {

  private String todo;

  public String getTodo() {
    return todo;
  }

  public void setTodo(final String todo) {
    this.todo = todo;
  }
}
