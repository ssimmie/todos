package net.ssimmie.todos.application.adapter.out.persistence;

import java.util.UUID;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface ReactiveChecklistRepository extends ReactiveCassandraRepository<Checklist, UUID> {}
