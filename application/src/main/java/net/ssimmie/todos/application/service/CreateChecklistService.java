package net.ssimmie.todos.application.service;

import static net.ssimmie.todos.domain.Checklist.namedEmptyChecklist;

import net.ssimmie.todos.application.port.in.CreateChecklistCommand;
import net.ssimmie.todos.application.port.in.CreateChecklistUseCase;
import net.ssimmie.todos.application.port.out.CreateChecklistPort;
import net.ssimmie.todos.domain.Checklist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Transactional
@Service
public class CreateChecklistService implements CreateChecklistUseCase {

  private static final Logger LOGGER = LoggerFactory.getLogger(CreateChecklistService.class);

  private final CreateChecklistPort createChecklistPort;

  public CreateChecklistService(final CreateChecklistPort createChecklistPort) {
    this.createChecklistPort = createChecklistPort;
  }

  @Override
  public Mono<Checklist> createChecklist(final CreateChecklistCommand createChecklistCommand) {
    final Checklist checklist = namedEmptyChecklist(createChecklistCommand.getChecklistName());

    LOGGER.info("{} has produced {}", createChecklistCommand, checklist);

    return createChecklistPort.insertChecklist(checklist).log();
  }
}
