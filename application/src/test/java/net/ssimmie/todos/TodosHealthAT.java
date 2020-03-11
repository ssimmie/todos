package net.ssimmie.todos;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class TodosHealthAT {

  private WebTestClient webTestClient;

  @BeforeEach
  public void setUp(
      final ApplicationContext applicationContext,
      final RestDocumentationContextProvider restDocumentation) {
    this.webTestClient =
        WebTestClient.bindToApplicationContext(applicationContext)
            .configureClient()
            .filter(documentationConfiguration(restDocumentation))
            .build();
  }

  @Test
  public void shouldRepresentApplicationStatusAtHealthResource(
      @Autowired final WebTestClient webClient) {
    this.webTestClient
        .get()
        .uri("/actuator/health")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .json("{\"status\":\"UP\"}")
        .consumeWith(document("healthcheck"));
  }
}
