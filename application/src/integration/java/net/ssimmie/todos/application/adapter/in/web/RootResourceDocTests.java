package net.ssimmie.todos.application.adapter.in.web;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.reactive.server.WebTestClient.bindToApplicationContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@Tag("docs")
@WebFluxTest(controllers = RootResource.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class RootResourceDocTests {

  private WebTestClient webTestClient;

  @BeforeEach
  public void setUp(
      final ApplicationContext applicationContext,
      final RestDocumentationContextProvider restDocumentation) {
    this.webTestClient =
        bindToApplicationContext(applicationContext)
            .configureClient()
            .filter(documentationConfiguration(restDocumentation))
            .build();
  }

  @Test
  public void shouldProvideEntryPointToAvailableTopLevelResources() {
    this.webTestClient
        .get()
        .uri("/")
        .accept(MediaTypes.HAL_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$._links.self.href")
        .isEqualTo("http://localhost:8080/")
        .jsonPath("$._links.tasks.href")
        .isEqualTo("http://localhost:8080/tasks")
        .consumeWith(document("root", links(halLinks(),
            linkWithRel("self").description("Link to the root resource"),
            linkWithRel("tasks").description("Link to the tasks resource"))));
  }
}
