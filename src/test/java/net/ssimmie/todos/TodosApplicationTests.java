package net.ssimmie.todos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class TodosApplicationTests {

  @Test
  public void shouldBeAnnotatedWithSpringBootApplication() {
    assertThat(TodosApplication.class.getAnnotationsByType(SpringBootApplication.class))
        .isNotNull();
  }

  @Test
  public void shouldBeAbleToStartTheApplication() {
    TodosApplication.main(new String[]{"--server.port=0", "--debug"});
  }
}
