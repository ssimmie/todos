package net.ssimmie.todos.api;

public class Todo {

  private final String title;

  public Todo(final String stitle) {
    this.title = stitle;
  }

  public String getTitle() {
    return title;
  }
}
