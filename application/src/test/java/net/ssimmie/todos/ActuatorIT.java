package net.ssimmie.todos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ActuatorIT {

  @Test
  public void shouldExposeBuildInfo(@Autowired WebTestClient webClient) {
    webClient.get()
        .uri("/actuator/health").exchange()
        .expectStatus()
        .isOk()
        .expectBody(Health.class)
        .consumeWith(new Consumer<EntityExchangeResult<Health>>() {
          @Override
          public void accept(EntityExchangeResult<Health> healthEntityExchangeResult) {
            assertThat(healthEntityExchangeResult.getResponseBody().getStatus()).isEqualTo("UP");
          }
        });
  }

  static class Health {
    private String status;

    public Health() {

    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }
  }
}
