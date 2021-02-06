package net.ssimmie.todos.application.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;

class CassandraConfigTest {

  @Test
  public void shouldDefaultToCreateIfNotExistsSchemaAction() {
    final CassandraConfig cassandraConfig = new CassandraConfig();

    assertThat(cassandraConfig.getSchemaAction()).isEqualTo(SchemaAction.CREATE_IF_NOT_EXISTS);
  }

  @Test
  public void shouldProvideSchemaActionAsSet() {
    final SchemaAction expectedSchemaAction = SchemaAction.CREATE;
    final CassandraConfig cassandraConfig = new CassandraConfig();
    setField(cassandraConfig, "schemaAction", expectedSchemaAction.name());

    assertThat(cassandraConfig.getSchemaAction()).isEqualTo(expectedSchemaAction);
  }

  @Test
  public void shouldProvideSchemaActionAsSetInLowerCase() {
    final SchemaAction expectedSchemaAction = SchemaAction.CREATE;
    final CassandraConfig cassandraConfig = new CassandraConfig();
    setField(cassandraConfig, "schemaAction", expectedSchemaAction.name().toLowerCase(Locale.ROOT));

    assertThat(cassandraConfig.getSchemaAction()).isEqualTo(expectedSchemaAction);
  }

  @Test
  public void shouldProvidePort() {
    final int expectedPort = 9999;
    final CassandraConfig cassandraConfig = new CassandraConfig();
    setField(cassandraConfig, "port", expectedPort);

    assertThat(cassandraConfig.getPort()).isEqualTo(expectedPort);
  }

  @Test
  public void shouldProvideContactPoints() {
    final String expectedContactPoints = "0.0.0.0";
    final CassandraConfig cassandraConfig = new CassandraConfig();
    setField(cassandraConfig, "contactPoints", expectedContactPoints);

    assertThat(cassandraConfig.getContactPoints()).isEqualTo(expectedContactPoints);
  }

  @Test
  public void shouldProvideBasePackagesForEntities() {
    final String expectedBasePackage = "net.ssimmie.todos.application.adapter.out.persistence";
    final CassandraConfig cassandraConfig = new CassandraConfig();

    assertThat(cassandraConfig.getEntityBasePackages()).containsExactly(expectedBasePackage);
  }

  @Test
  public void shouldCreateKeyspaceIfNotExists() {
    final String expectedKeyspace = "keyspace";
    final CassandraConfig cassandraConfig = new CassandraConfig();
    setField(cassandraConfig, "keyspace", expectedKeyspace);

    assertThat(cassandraConfig.getKeyspaceCreations())
        .hasSize(1)
        .allMatch(CreateKeyspaceSpecification::getIfNotExists)
        .allMatch(cks -> expectedKeyspace.equals(cks.getName().asInternal()));
  }
}
