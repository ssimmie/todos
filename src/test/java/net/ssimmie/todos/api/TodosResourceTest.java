package net.ssimmie.todos.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import reactor.test.StepVerifier;

public class TodosResourceTest {

  private final TodosResource todosResource = new TodosResource();

  @Test
  public void shouldReturnAllTodos() {
    StepVerifier.create(todosResource.getTodos())
        .assertNext(todo -> assertThat(todo.getTitle()).isEqualTo("Paint room"))
        .assertNext(todo -> assertThat(todo.getTitle()).isEqualTo("Order shelves"))
        .verifyComplete();
  }

}