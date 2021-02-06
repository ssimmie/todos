package net.ssimmie.todos.application;

import static net.ssimmie.todos.application.TodosApplication.main;
import static org.assertj.core.api.Assertions.assertThat;
import static org.cassandraunit.utils.EmbeddedCassandraServerHelper.startEmbeddedCassandra;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import java.io.IOException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
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
  public void shouldBeAbleToStartTheApplication() throws IOException {
    EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
    startEmbeddedCassandra("embedded-cassandra.yaml");
    final CqlSession session = EmbeddedCassandraServerHelper.getSession();
    final ResultSet resultSet =
        session.execute(
            "CREATE KEYSPACE todos WITH REPLICATION = {\n"
                + "'class': 'SimpleStrategy',\n"
                + "'replication_factor': 1\n"
                + "};");

    assertDoesNotThrow(() -> main(new String[] {"--server.port=0"}));
  }
}
