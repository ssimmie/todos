package net.ssimmie.todos.application.adapter.out.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

final class StubChecklistRepository implements ReactiveChecklistRepository {

  private final Map<UUID, Checklist> store = new HashMap<>();

  @Override
  public <S extends Checklist> Mono<S> insert(final S entity) {
    store.put(entity.getId(), entity);
    return Mono.just(entity);
  }

  @Override
  public <S extends Checklist> Flux<S> insert(final Iterable<S> entities) {
    entities.forEach(s -> store.put(s.getId(), s));
    return Flux.fromIterable(entities);
  }

  @Override
  public <S extends Checklist> Flux<S> insert(final Publisher<S> entities) {
    return Flux.from(entities).doOnNext(s -> store.put(s.getId(), s));
  }

  @Override
  public <S extends Checklist> Mono<S> save(final S entity) {
    return this.insert(entity);
  }

  @Override
  public <S extends Checklist> Flux<S> saveAll(final Iterable<S> entities) {
    return this.insert(entities);
  }

  @Override
  public <S extends Checklist> Flux<S> saveAll(final Publisher<S> entityStream) {
    return this.insert(entityStream);
  }

  @Override
  public Mono<Checklist> findById(final UUID uuid) {
    return Mono.justOrEmpty(store.get(uuid));
  }

  @Override
  public Mono<Checklist> findById(final Publisher<UUID> id) {
    return null;
  }

  @Override
  public Mono<Boolean> existsById(final UUID uuid) {
    return null;
  }

  @Override
  public Mono<Boolean> existsById(final Publisher<UUID> id) {
    return null;
  }

  @Override
  public Flux<Checklist> findAll() {
    return null;
  }

  @Override
  public Flux<Checklist> findAllById(final Iterable<UUID> iterable) {
    return null;
  }

  @Override
  public Flux<Checklist> findAllById(final Publisher<UUID> publisher) {
    return null;
  }

  @Override
  public Mono<Long> count() {
    return null;
  }

  @Override
  public Mono<Void> deleteById(final UUID uuid) {
    return null;
  }

  @Override
  public Mono<Void> deleteById(final Publisher<UUID> id) {
    return null;
  }

  @Override
  public Mono<Void> delete(final Checklist entity) {
    return null;
  }

  @Override
  public Mono<Void> deleteAllById(Iterable<? extends UUID> uuids) {
    return null;
  }

  @Override
  public Mono<Void> deleteAll(final Iterable<? extends Checklist> entities) {
    return null;
  }

  @Override
  public Mono<Void> deleteAll(final Publisher<? extends Checklist> entityStream) {
    return null;
  }

  @Override
  public Mono<Void> deleteAll() {
    return null;
  }
}
