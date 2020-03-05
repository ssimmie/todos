package net.ssimmie.todos.domain;

public class Todo {

  private final String task;

  public Todo(final String task) {
    this.task = task;
  }

  public static Todo fromTask(final String task) {
    return new Todo(task);
  }

  public String getTask() {
    return task;
  }
}
