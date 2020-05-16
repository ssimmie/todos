package net.ssimmie.todos;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Tag;

@Tag("architecture")
@Tag("slow")
@AnalyzeClasses(packages = "net.ssimmie.todos")
public class ArchitectureTests {

  @ArchTest
  public static final ArchRule SHOULD_RESPECT_LAYERED_ARCHITECTURE =
      layeredArchitecture()
          .layer("Controller")
          .definedBy("net.ssimmie.todos")
          .layer("Domain")
          .definedBy("net.ssimmie.todos.domain")
          .whereLayer("Controller")
          .mayNotBeAccessedByAnyLayer()
          .whereLayer("Domain")
          .mayOnlyBeAccessedByLayers("Controller");
}
