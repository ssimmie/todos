package net.ssimmie.todos.application.adapter.in.web;

import static java.util.Objects.requireNonNull;
import static net.ssimmie.todos.domain.Checklist.namedEmptyChecklist;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.CREATED;

import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ChecklistsResourceTest {

  @Test
  public void shouldReturnSelfLink() {
    final ChecklistsResource checklistsResource =
        new ChecklistsResource(c -> namedEmptyChecklist(c.getChecklistName()));

    final Mono<RepresentationModel<?>> checklistsResourceRepresentationMono =
        checklistsResource.get();

    StepVerifier.create(checklistsResourceRepresentationMono)
        .assertNext(
            checklists -> {
              assertTrue(checklists.hasLink("self"));
              assertThat(checklists.getLink("self")).map(Link::getHref).hasValue("/checklists");
            })
        .verifyComplete();
  }

  @Test
  public void shouldCreateChecklist() {
    final String expectedChecklist = "derp";
    final ChecklistsResource checklistsResource =
        new ChecklistsResource(c -> namedEmptyChecklist(c.getChecklistName()));

    final Checklist checklist = new Checklist();
    checklist.setName(expectedChecklist);
    final Mono<ResponseEntity<EntityModel<Checklist>>> responseEntityMono =
        checklistsResource.create(checklist);

    StepVerifier.create(responseEntityMono)
        .assertNext(
            r -> {
              assertThat(r.getStatusCode()).isEqualTo(CREATED);
              assertThat(r.getHeaders().getLocation()).hasPath("/checklists");

              final Checklist actualChecklist = requireNonNull(r.getBody()).getContent();
              assertThat(requireNonNull(actualChecklist).getName()).isEqualTo(expectedChecklist);
            })
        .verifyComplete();
  }

  @Test
  public void shouldRejectUnnamedChecklist() {
    final ChecklistsResource checklistsResource =
        new ChecklistsResource(c -> namedEmptyChecklist(c.getChecklistName()));

    final Checklist checklist = new Checklist();
    final Mono<ResponseEntity<EntityModel<Checklist>>> responseEntityMono =
        checklistsResource.create(checklist);

    StepVerifier.create(responseEntityMono)
        .verifyErrorSatisfies(
            throwable ->
                assertThat(throwable)
                    .isInstanceOf(ConstraintViolationException.class)
                    .hasMessage("checklistName: Name is mandatory"));
  }
}
