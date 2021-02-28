package net.ssimmie.todos.application.service;

import net.ssimmie.todos.application.port.in.GetChecklistQuery;
import net.ssimmie.todos.application.port.out.LoadChecklistPort;
import net.ssimmie.todos.domain.Checklist;
import net.ssimmie.todos.domain.ChecklistId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Transactional
@Service
public class GetChecklistService implements GetChecklistQuery {

  private static final Logger LOGGER = LoggerFactory.getLogger(GetChecklistService.class);

  private final LoadChecklistPort loadChecklistPort;

  public GetChecklistService(final LoadChecklistPort loadChecklistPort) {
    this.loadChecklistPort = loadChecklistPort;
  }

  @Override
  public Mono<Checklist> getChecklist(final ChecklistId checklistId) {
    LOGGER.info("Searching for {}", checklistId);
    return loadChecklistPort.loadCheckList(checklistId);
  }
}
