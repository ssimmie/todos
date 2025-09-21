package net.ssimmie.todos.infrastructure;

import java.util.List;
import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.cassandra.CfnKeyspace;
import software.amazon.awscdk.services.cassandra.CfnTable;
import software.constructs.Construct;

/**
 * Amazon Keyspaces (Cassandra) infrastructure stack. Creates managed Cassandra-compatible database
 * with tables for Todos application.
 */
public class KeyspacesStack extends Stack {

  private static final String KEYSPACE_NAME = "todos";
  private final String keyspaceName;

  /**
   * Creates a new KeyspacesStack with managed Cassandra database.
   *
   * @param scope the parent construct
   * @param id the construct ID
   * @param props the stack properties
   */
  public KeyspacesStack(final Construct scope, final String id, final StackProps props) {
    super(scope, id, props);

    CfnKeyspace keyspace = createKeyspace();
    this.keyspaceName = keyspace.getKeyspaceName();

    createChecklistTable(keyspace);
    createTodoTable(keyspace);

    // Output keyspace name for application configuration
    CfnOutput.Builder.create(this, "KeyspaceName")
        .value(keyspaceName)
        .description("Keyspace name for Todos application")
        .build();
  }

  /** Creates the main keyspace for the application. */
  private CfnKeyspace createKeyspace() {
    return CfnKeyspace.Builder.create(this, "TodosKeyspace").keyspaceName(KEYSPACE_NAME).build();
  }

  /** Creates checklist table matching the domain model. */
  private void createChecklistTable(final CfnKeyspace keyspace) {
    CfnTable.Builder.create(this, "ChecklistTable")
        .keyspaceName(keyspace.getKeyspaceName())
        .tableName("checklist")
        .partitionKeyColumns(
            List.of(CfnTable.ColumnProperty.builder().columnName("id").columnType("text").build()))
        .regularColumns(
            List.of(
                CfnTable.ColumnProperty.builder().columnName("name").columnType("text").build(),
                CfnTable.ColumnProperty.builder()
                    .columnName("created_at")
                    .columnType("timestamp")
                    .build(),
                CfnTable.ColumnProperty.builder()
                    .columnName("updated_at")
                    .columnType("timestamp")
                    .build()))
        .pointInTimeRecoveryEnabled(true)
        .defaultTimeToLive(0) // No TTL
        .build();
  }

  /** Creates todo table matching the domain model. */
  private void createTodoTable(final CfnKeyspace keyspace) {
    CfnTable.Builder.create(this, "TodoTable")
        .keyspaceName(keyspace.getKeyspaceName())
        .tableName("todo")
        .partitionKeyColumns(
            List.of(
                CfnTable.ColumnProperty.builder()
                    .columnName("checklist_id")
                    .columnType("text")
                    .build()))
        .clusteringKeyColumns(
            List.of(
                CfnTable.ClusteringKeyColumnProperty.builder()
                    .column(
                        CfnTable.ColumnProperty.builder()
                            .columnName("id")
                            .columnType("text")
                            .build())
                    .build()))
        .regularColumns(
            List.of(
                CfnTable.ColumnProperty.builder().columnName("task").columnType("text").build(),
                CfnTable.ColumnProperty.builder()
                    .columnName("completed")
                    .columnType("boolean")
                    .build(),
                CfnTable.ColumnProperty.builder()
                    .columnName("created_at")
                    .columnType("timestamp")
                    .build(),
                CfnTable.ColumnProperty.builder()
                    .columnName("updated_at")
                    .columnType("timestamp")
                    .build()))
        .pointInTimeRecoveryEnabled(true)
        .defaultTimeToLive(0) // No TTL
        .build();
  }

  public String getKeyspaceName() {
    return keyspaceName;
  }
}
