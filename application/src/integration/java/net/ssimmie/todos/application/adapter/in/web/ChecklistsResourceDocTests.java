package net.ssimmie.todos.application.adapter.in.web;

import static net.ssimmie.todos.domain.Checklist.namedEmptyChecklist;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.reactive.server.WebTestClient.bindToApplicationContext;

import net.ssimmie.todos.application.port.in.CreateChecklistUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@Tag("docs")
@WebFluxTest(controllers = ChecklistsResource.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class ChecklistsResourceDocTests {

  private WebTestClient webTestClient;

  @MockBean
  private CreateChecklistUseCase createChecklistUseCase;

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
  public void shouldListAllChecklists() {
    this.webTestClient
        .get()
        .uri("/checklists")
        .accept(HAL_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$._links.self.href")
        .isEqualTo("http://localhost:8080/checklists")
        .consumeWith(document("get-checklists",
            links(halLinks(), linkWithRel("self").description("Link to the checklists resource"))));
  }

  @Test
  public void shouldCreateChecklists() {
    final Checklist expectedChecklist = new Checklist();
    expectedChecklist.setName("derp");
    when(createChecklistUseCase.createChecklist(any()))
        .thenReturn(namedEmptyChecklist(expectedChecklist.getName()));

    this.webTestClient
        .post()
        .uri("/checklists")
        .contentType(APPLICATION_JSON)
        .accept(HAL_JSON)
        .bodyValue(expectedChecklist)
        .exchange()
        .expectBody()
        .jsonPath("$.name")
        .isEqualTo("derp")
        .jsonPath("$._links.self.href")
        .isEqualTo("http://localhost:8080/checklists")
        .consumeWith(document("create-checklists",
            links(halLinks(), linkWithRel("self").description("Link to the checklists resource"))));
  }
}
