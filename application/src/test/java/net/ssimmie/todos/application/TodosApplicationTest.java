package net.ssimmie.todos.application;

import static net.ssimmie.todos.application.TodosApplication.main;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

class TodosApplicationTest {

  @Test
  public void shouldBeAnnotatedWithSpringBootApplication() {
    assertNotNull(TodosApplication.class.getAnnotationsByType(SpringBootApplication.class));
  }

  @Test
  public void shouldBeAbleToStartTheApplication() {
    try {
      main(new String[] {"--logging.level.root=ERROR", "--server.port=0"});
    } catch (Exception e) {
      fail("Application startup failed");
    }
  }
}
