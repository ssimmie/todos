package net.ssimmie.todos.application.adapter.out.persistence;

import static java.util.UUID.randomUUID;
import static net.ssimmie.todos.application.adapter.out.persistence.Checklist.checklistEntityFromDomain;
import static net.ssimmie.todos.domain.Checklist.knownNamedEmptyChecklist;
import static net.ssimmie.todos.domain.Checklist.namedEmptyChecklist;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.jparams.verifier.tostring.ToStringVerifier;
import java.util.Optional;
import net.ssimmie.todos.domain.ChecklistName;
import org.junit.jupiter.api.Test;

class ChecklistTest {

  @Test
  public void shouldCreateEntityFromUnidentifiedDomainType() {
    final var expectedName = "test";
    final var checklist = checklistEntityFromDomain(namedEmptyChecklist(expectedName));

    assertThat(checklist.getId()).isNotNull();
    assertThat(checklist.getName()).isEqualTo(expectedName);
  }

  @Test
  public void shouldCreateEntityFromIdentifiedDomainType() {
    final var expectedId = randomUUID();
    final var expectedName = "test";
    final var checklist =
        checklistEntityFromDomain(knownNamedEmptyChecklist(expectedId, expectedName));

    assertThat(checklist.getId()).isEqualTo(expectedId);
    assertThat(checklist.getName()).isEqualTo(expectedName);
  }

  @Test
  public void shouldCreateEntityFromDomainTypeWithoutId() {
    final var expectedName = "test";
    final net.ssimmie.todos.domain.Checklist mockChecklist =
        mock(net.ssimmie.todos.domain.Checklist.class);
    when(mockChecklist.getId()).thenReturn(Optional.empty());
    when(mockChecklist.getName()).thenReturn(ChecklistName.newChecklistName(expectedName));

    final var checklist = checklistEntityFromDomain(mockChecklist);

    assertThat(checklist.getId()).isNotNull();
    assertThat(checklist.getName()).isEqualTo(expectedName);
  }

  @Test
  public void shouldIncludeToString() {
    ToStringVerifier.forClass(Checklist.class).verify();
  }
}
