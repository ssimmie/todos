package net.ssimmie.todos.domain;

import java.util.Objects;
import java.util.UUID;

public final class ChecklistId {

  private final UUID value;

  private ChecklistId(final UUID value) {
    this.value = value;
  }

  public static ChecklistId newChecklistId(final UUID value) {
    return new ChecklistId(value);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ChecklistId that = (ChecklistId) o;
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
