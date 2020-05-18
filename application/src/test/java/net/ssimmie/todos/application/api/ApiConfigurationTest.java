package net.ssimmie.todos.application.api;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;
import static org.springframework.hateoas.support.WebStack.WEBFLUX;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.ServerCodecConfigurer.ServerDefaultCodecs;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.EnableWebFlux;

class ApiConfigurationTest {

  @Test
  public void shouldBeDetectedBySpringScanning() {
    assertNotNull(ApiConfiguration.class.getAnnotationsByType(Component.class));
  }

  @Test
  public void shouldEnableWebFlux() {
    assertNotNull(ApiConfiguration.class.getAnnotationsByType(EnableWebFlux.class));
  }

  @Test
  public void shouldSupportHalWithWebFlux() {
    final EnableHypermediaSupport[] supports =
        ApiConfiguration.class.getAnnotationsByType(EnableHypermediaSupport.class);

    assertAll(
        "Hypermedia",
        () -> assertEquals(WEBFLUX, supports[0].stacks()[0], "WebFlux is not enabled"),
        () -> assertEquals(HAL, supports[0].type()[0], "HAL is not enabled"));
  }

  @Test
  public void shouldEnableLoggingOnRequestResponses() {
    final ServerCodecConfigurer serverCodecConfigurer = mock(ServerCodecConfigurer.class);
    final ServerDefaultCodecs serverDefaultCodecs = mock(ServerDefaultCodecs.class);
    when(serverCodecConfigurer.defaultCodecs()).thenReturn(serverDefaultCodecs);

    new ApiConfiguration().configureHttpMessageCodecs(serverCodecConfigurer);

    verify(serverDefaultCodecs).enableLoggingRequestDetails(true);
  }
}
