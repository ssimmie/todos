package net.ssimmie.todos;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "net.ssimmie.todos")
public class ArchitectureTests {

  @ArchTest
  public static final ArchRule shouldRespectLayeredArchitecture =
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
