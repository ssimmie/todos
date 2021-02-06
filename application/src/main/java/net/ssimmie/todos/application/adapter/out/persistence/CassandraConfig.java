package net.ssimmie.todos.application.adapter.out.persistence;

import static com.datastax.oss.driver.api.core.CqlIdentifier.fromInternal;
import static java.util.Optional.ofNullable;
import static org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification.createKeyspace;

import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {

  @Value("${spring.data.cassandra.contact-points:127.0.0.1}")
  private String contactPoints;

  @Value("${spring.data.cassandra.port:9042}")
  private int port;

  @Value("${spring.data.cassandra.schema-action:CREATE_IF_NOT_EXISTS}")
  private String schemaAction;

  @Value("${spring.data.cassandra.keyspace-name}")
  private String keyspace;

  @Override
  protected String getKeyspaceName() {
    return keyspace;
  }

  @Override
  protected String getContactPoints() {
    return contactPoints;
  }

  @Override
  protected int getPort() {
    return port;
  }

  @Override
  public SchemaAction getSchemaAction() {
    return SchemaAction.valueOf(
        ofNullable(schemaAction)
            .map(s -> s.toUpperCase(Locale.ROOT))
            .orElse("CREATE_IF_NOT_EXISTS"));
  }

  @Override
  public String[] getEntityBasePackages() {
    return new String[] {CassandraConfig.class.getPackageName()};
  }

  @Override
  protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {

    return List.of(
        createKeyspace(fromInternal(getKeyspaceName())).ifNotExists().withSimpleReplication());
  }
}
