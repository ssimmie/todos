package net.ssimmie.todos.domain;

import com.jparams.verifier.tostring.ToStringVerifier;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static net.ssimmie.todos.domain.Checklist.*;
import static org.assertj.core.api.Assertions.assertThat;

class ChecklistTest {

    @Test
    public void shouldCreateNamedEmptyChecklistWithUniqueId() {
        final Checklist jobs = namedEmptyChecklist("jobs");

        assertThat(jobs.getId()).isNotEmpty();
    }

    @Test
    public void shouldCreateNamedEmptyChecklistWithNoTodos() {
        final Checklist jobs = namedEmptyChecklist("jobs");

        assertThat(jobs.getTodos()).isEmpty();
    }

    @Test
    public void shouldCreateNamedEmptyChecklist() {
        final String expectedName = "jobs";
        final Checklist jobs = namedEmptyChecklist(expectedName);

        assertThat(jobs.getName().getValue()).isEqualTo(expectedName);
    }

    @Test
    public void shouldCreateKnownNamedEmptyChecklist() {
        final UUID expectedId = UUID.randomUUID();
        final String expectedName = "jobs";
        final Checklist jobs = knownNamedEmptyChecklist(expectedId, expectedName);

        assertThat(jobs.getId()).map(ChecklistId::getValue).hasValue(expectedId);
        assertThat(jobs.getName().getValue()).isEqualTo(expectedName);
    }

    @Test
    public void shouldCreateNamedChecklistWithTodo() {
        final var expectedTask = "task";

        final Checklist jobs = namedChecklist("name", new Todo(expectedTask));

        assertThat(jobs.getTodos()).hasSize(1);
        assertThat(jobs.getTodos()).extracting(Todo::getTask).containsOnly(expectedTask);
    }

    @Test
    public void shouldCreateNamedChecklistWithMultipleTodos() {
        final var expectedTask1 = "task1";
        final var expectedTask2 = "task2";
        final var expectedTask3 = "task3";

        final Checklist jobs = namedChecklist("name", new Todo(expectedTask1), new Todo(expectedTask2),
                new Todo(expectedTask3));

        assertThat(jobs.getTodos()).hasSize(3);
        assertThat(jobs.getTodos()).extracting(Todo::getTask).containsExactly(expectedTask1, expectedTask2,
                expectedTask3);
    }

    @Test
    public void shouldSupportToString() {
        ToStringVerifier.forClass(Checklist.class).verify();
    }
}
