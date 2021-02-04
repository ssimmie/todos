package net.ssimmie.todos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TodoTest {

  @Test
  public void shouldCreateTodoFromTask() {
    final String expectedTask = "expectedTask";
    final Todo todo = Todo.fromTask(expectedTask);

    assertThat(todo.getTask()).isEqualTo(expectedTask);
  }
}
