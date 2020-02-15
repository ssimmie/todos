package net.ssimmie.todos;

import static net.ssimmie.todos.TodosApplication.main;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

class TodosApplicationTests {

  @Test
  public void shouldBeAnnotatedWithSpringBootApplication() {
    assertNotNull(TodosApplication.class.getAnnotationsByType(SpringBootApplication.class));
  }

  @Test
  public void shouldBeAbleToStartTheApplication() {
    try {
      main(new String[]{"--spring.main.banner-mode=off", "--server.port=0", "--debug"});
    } catch (Exception e) {
      fail("Application startup failed");
    }
  }
}
