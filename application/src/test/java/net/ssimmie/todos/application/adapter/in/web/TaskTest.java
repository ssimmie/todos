package net.ssimmie.todos.application.adapter.in.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class TaskTest {

  @Test
  public void shouldContainTodo() {
    final String expectedTodo = "todo";
    final Task task = new Task();
    task.setTodo(expectedTodo);

    assertThat(task.getTodo()).isEqualTo(expectedTodo);
  }
}
