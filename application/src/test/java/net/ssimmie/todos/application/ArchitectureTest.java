package net.ssimmie.todos.application;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Tag;

@Tag("architecture")
@AnalyzeClasses(packages = "net.ssimmie.todos")
public class ArchitectureTest {

  @ArchTest
  public static final ArchRule SHOULD_RESPECT_LAYERED_ARCHITECTURE =
      layeredArchitecture()
          .layer("Controller")
          .definedBy("net.ssimmie.todos.application.api")
          .layer("Domain")
          .definedBy("net.ssimmie.todos.domain")
          .whereLayer("Controller")
          .mayNotBeAccessedByAnyLayer()
          .whereLayer("Domain")
          .mayOnlyBeAccessedByLayers("Controller");
}
