package net.ssimmie.todos.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TodoTest {

  @Test
  public void shouldCreateWithTitle() {
    final String expectedTitle = "title";
    final Todo todo = new Todo(expectedTitle);

    assertThat(todo.getTitle()).isEqualTo(expectedTitle);
  }

}
