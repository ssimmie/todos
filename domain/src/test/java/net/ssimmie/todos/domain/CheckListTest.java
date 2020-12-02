package net.ssimmie.todos.domain;

import static net.ssimmie.todos.domain.CheckList.namedEmptyCheckList;
import static net.ssimmie.todos.domain.CheckListName.newCheckListName;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CheckListTest {

  @Test
  public void shouldCreateNamedEmptyCheckListWithUniqueId() {
    final CheckList jobs = namedEmptyCheckList(newCheckListName("jobs"));

    assertThat(jobs.getId()).isNotEmpty();
  }

  @Test
  public void shouldCreateNamedEmptyCheckListWithNoTodos() {
    final CheckList jobs = namedEmptyCheckList(newCheckListName("jobs"));

    assertThat(jobs.getTodos()).isEmpty();
  }

  @Test
  public void shouldCreateNamedEmptyCheckList() {
    final String expectedName = "jobs";
    final CheckList jobs = namedEmptyCheckList(newCheckListName(expectedName));

    assertThat(jobs.getName().getValue()).isEqualTo(expectedName);
  }
}
