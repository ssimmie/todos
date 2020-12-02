package net.ssimmie.todos.domain;

import java.util.Objects;
import java.util.UUID;

public final class CheckListId {

  private final UUID value;

  private CheckListId(final UUID value) {
    this.value = value;
  }

  public static CheckListId newCheckListId(final UUID value) {
    return new CheckListId(value);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final CheckListId that = (CheckListId) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  public UUID getValue() {
    return value;
  }
}
