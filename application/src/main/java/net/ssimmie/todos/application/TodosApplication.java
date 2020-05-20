package net.ssimmie.todos.application;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@EnableHypermediaSupport(type = HAL)
@SpringBootApplication
public class TodosApplication {

  public static void main(String[] args) {
    SpringApplication.run(TodosApplication.class, args);
  }
}
