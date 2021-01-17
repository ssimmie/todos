package net.ssimmie.todos.domain;

import static java.util.Collections.emptyList;
import static java.util.UUID.randomUUID;
import static net.ssimmie.todos.domain.ChecklistId.newChecklistId;
import static net.ssimmie.todos.domain.ChecklistName.newChecklistName;

import java.util.List;
import java.util.Optional;

public class Checklist {

  private final ChecklistId id;
  private final ChecklistName name;
  private final List<Todo> todos;

  private Checklist(final ChecklistId id, final ChecklistName name, final List<Todo> todos) {
    this.id = id;
    this.name = name;
    this.todos = todos;
  }

  public static Checklist namedEmptyChecklist(final String name) {
    return new Checklist(newChecklistId(randomUUID()), newChecklistName(name), emptyList());
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
