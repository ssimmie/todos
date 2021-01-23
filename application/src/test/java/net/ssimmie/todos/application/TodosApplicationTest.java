package net.ssimmie.todos.application;

import static net.ssimmie.todos.application.TodosApplication.main;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;

class TodosApplicationTest {

  @Test
  public void shouldBeAnnotatedWithSpringBootApplication() {
    assertThat(TodosApplication.class).hasAnnotation(SpringBootApplication.class);
  }

  @Test
  public void shouldBeAnnotatedWithEnableHypermediaSupportForHal() {
    assertThat(TodosApplication.class.getAnnotation(EnableHypermediaSupport.class).type())
        .containsExactly(HAL);
  }

  @Test
  public void shouldBeAbleToStartTheApplication() {
    assertDoesNotThrow(() -> main(new String[] {"--server.port=0"}));
  }
}
