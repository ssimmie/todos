package net.ssimmie.todos.domain;

import java.util.Objects;

public final class CheckListName {

  private final String value;

  private CheckListName(final String value) {
    this.value = value;
  }

  public static CheckListName newCheckListName(final String value) {
    return new CheckListName(value);
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
    final CheckListName that = (CheckListName) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
