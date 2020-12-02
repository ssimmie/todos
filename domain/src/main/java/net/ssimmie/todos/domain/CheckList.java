package net.ssimmie.todos.domain;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;
import static net.ssimmie.todos.domain.CheckListId.newCheckListId;

import java.util.List;
import java.util.Optional;

public class CheckList {

  private final CheckListId id;
  private final CheckListName name;
  private final List<Todo> todos;

  private CheckList(final CheckListId id, final CheckListName name, final List<Todo> todos) {
    this.id = id;
    this.name = name;
    this.todos = todos;
  }

  public static CheckList namedEmptyCheckList(final CheckListName name) {
    return new CheckList(newCheckListId(randomUUID()), name, emptyList());
  }

  public Optional<CheckListId> getId() {
    return ofNullable(id);
  }

  public CheckListName getName() {
    return name;
  }

  public List<Todo> getTodos() {
    return todos;
  }
}
