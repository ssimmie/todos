package net.ssimmie.todos.domain;

import static java.util.Collections.emptyList;
import static java.util.UUID.randomUUID;
import static net.ssimmie.todos.domain.ChecklistId.newChecklistId;
import static net.ssimmie.todos.domain.ChecklistName.newChecklistName;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Checklist {

  private final ChecklistId id;
  private final ChecklistName name;
  private final List<Todo> todos;

  private Checklist(final ChecklistId id, final ChecklistName name, final List<Todo> todos) {
    this.id = id;
    this.name = name;
    this.todos = todos;
  }

  public static Checklist knownNamedEmptyChecklist(final UUID id, final String name) {
    return new Checklist(newChecklistId(id), newChecklistName(name), emptyList());
  }

  public static Checklist namedEmptyChecklist(final String name) {
    return new Checklist(newChecklistId(randomUUID()), newChecklistName(name), emptyList());
  }

  public static Checklist namedChecklist(final String name, final Todo... todos) {
    return new Checklist(newChecklistId(randomUUID()), newChecklistName(name),
            Arrays.asList(todos));
  }

  @Override
  public String toString() {
    return "Checklist{" + "id=" + id + ", name=" + name + ", todos=" + todos + '}';
  }

  public Optional<ChecklistId> getId() {
    return Optional.ofNullable(id);
  }

  public ChecklistName getName() {
    return name;
  }

  public List<Todo> getTodos() {
    return todos;
  }
}
