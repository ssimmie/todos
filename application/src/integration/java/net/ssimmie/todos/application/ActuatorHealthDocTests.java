package net.ssimmie.todos.application;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.reactive.server.WebTestClient.bindToApplicationContext;

import org.cassandraunit.spring.CassandraDataSet;
import org.cassandraunit.spring.CassandraUnitDependencyInjectionTestExecutionListener;
import org.cassandraunit.spring.EmbeddedCassandra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.reactive.server.WebTestClient;

@CassandraDataSet(value = "create-keyspace.cql")
@EmbeddedCassandra(configuration = "embedded-cassandra.yaml")
@TestExecutionListeners({CassandraUnitDependencyInjectionTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class})
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {
    "spring.data.cassandra.local-datacenter=datacenter1",
    "spring.data.cassandra.keyspace-name=todos",
    "spring.data.cassandra.schema-action=create_if_not_exists"
})
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class ActuatorHealthDocTests {

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
  public void shouldReportStatusUpWhenHealthy() {
    this.webTestClient
        .get()
        .uri("/actuator/health")
        .accept(APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.status")
        .isNotEmpty()
        .jsonPath("$.status")
        .isEqualTo("UP")
        .consumeWith(document("healthcheck"));
  }
}
