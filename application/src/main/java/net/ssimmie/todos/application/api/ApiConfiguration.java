package net.ssimmie.todos.application.api;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;
import static org.springframework.hateoas.support.WebStack.WEBFLUX;

import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Component
@EnableWebFlux
@EnableHypermediaSupport(type = HAL, stacks = WEBFLUX)
class ApiConfiguration implements WebFluxConfigurer {

  @Override
  public void configureHttpMessageCodecs(final ServerCodecConfigurer configurer) {
    configurer.defaultCodecs().enableLoggingRequestDetails(true);
  }
}
