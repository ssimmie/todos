package net.ssimmie.todos.application.adapter.out.persistence;

import static com.datastax.oss.driver.api.core.uuid.Uuids.timeBased;
import static net.ssimmie.todos.domain.ChecklistId.newChecklistId;

import java.util.UUID;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
class Checklist {

  @PrimaryKey private final UUID id;
  private final String name;

  public Checklist(final UUID id, final String name) {
    this.id = id;
    this.name = name;
  }

  public static Checklist checklistEntityFromDomain(
      final net.ssimmie.todos.domain.Checklist checklist) {
    return new Checklist(
        checklist.getId().orElseGet(() -> newChecklistId(timeBased())).getValue(),
        checklist.getName().getValue());
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Checklist{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
