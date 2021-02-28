package net.ssimmie.todos.domain;

import java.util.Objects;

public final class ChecklistName {

  private final String value;

  private ChecklistName(final String value) {
    this.value = value;
  }

  public static ChecklistName newChecklistName(final String value) {
    return new ChecklistName(value);
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ChecklistName that = (ChecklistName) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return "ChecklistName{" + "value='" + value + '\'' + '}';
  }
}
