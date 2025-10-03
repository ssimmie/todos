package net.ssimmie.todos.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

class KeyspacesStackTest {

  @Test
  void constructor_shouldCreateKeyspace() {
    App app = new App();
    StackProps stackProps =
        StackProps.builder()
            .env(Environment.builder().account("123456789012").region("eu-west-2").build())
            .build();

    KeyspacesStack keyspacesStack = new KeyspacesStack(app, "TestKeyspacesStack", stackProps);

    assertThat(keyspacesStack.getKeyspaceName()).isNotNull();
    assertThat(keyspacesStack.getKeyspaceName()).isEqualTo("todos");
  }

  @Test
  void getKeyspaceName_shouldReturnConsistentValue() {
    App app = new App();
    StackProps stackProps =
        StackProps.builder()
            .env(Environment.builder().account("123456789012").region("eu-west-2").build())
            .build();

    KeyspacesStack keyspacesStack = new KeyspacesStack(app, "TestKeyspacesStack2", stackProps);
    String name1 = keyspacesStack.getKeyspaceName();
    String name2 = keyspacesStack.getKeyspaceName();

    assertThat(name1).isEqualTo("todos");
    assertThat(name2).isEqualTo("todos");
    assertThat(name1).isSameAs(name2);
  }

  @Test
  void keyspaceName_shouldBeConsistentAcrossInstances() {
    App app = new App();
    StackProps stackProps =
        StackProps.builder()
            .env(Environment.builder().account("123456789012").region("eu-west-2").build())
            .build();

    KeyspacesStack stack1 = new KeyspacesStack(app, "Stack1", stackProps);
    KeyspacesStack stack2 = new KeyspacesStack(app, "Stack2", stackProps);

    assertThat(stack1.getKeyspaceName()).isEqualTo(stack2.getKeyspaceName());
    assertThat(stack1.getKeyspaceName()).isEqualTo("todos");
  }
}
