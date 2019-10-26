package net.ssimmie.todos.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/todos")
public class TodosResource {

  @GetMapping(produces = "application/json;charset=UTF-8")
  public Flux<Todo> getTodos() {
    return Flux.just(new Todo("Paint room"),new Todo("Order shelves"));
  }

}
