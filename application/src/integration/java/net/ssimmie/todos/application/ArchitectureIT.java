package net.ssimmie.todos.application;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "net.ssimmie.todos")
public class ArchitectureIT {

  @ArchTest
  public static final ArchRule SHOULD_RESPECT_CLEAN_ARCHITECTURE =
      layeredArchitecture()
          .layer("Controller")
          .definedBy("net.ssimmie.todos.application.adapter.in.web")
          .layer("Service")
          .definedBy("net.ssimmie.todos.application.service")
          .layer("Inbound Ports")
          .definedBy("net.ssimmie.todos.application.port.in")
          .layer("Outbound Ports")
          .definedBy("net.ssimmie.todos.application.port.out")
          .layer("Persistence Adapter")
          .definedBy("net.ssimmie.todos.application.adapter.out.persistence")
          .layer("Domain")
          .definedBy("net.ssimmie.todos.domain")
          .whereLayer("Controller")
          .mayNotBeAccessedByAnyLayer()
          .whereLayer("Domain")
          .mayOnlyBeAccessedByLayers(
              "Controller", "Service", "Inbound Ports", "Outbound Ports", "Persistence Adapter");
}
