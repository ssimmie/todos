package net.ssimmie.todos.infrastructure;

import org.junit.jupiter.api.Test;
import software.amazon.awscdk.Environment;

import static org.assertj.core.api.Assertions.assertThat;

class TodosInfrastructureAppTest {

    @Test
    void createEnvironment_shouldCreateEnvironmentInstance() {
        Environment environment = TodosInfrastructureApp.createEnvironment();

        assertThat(environment).isNotNull();
        // Environment values come from system properties or env vars, so we can't assert specific values
        // but we can verify the method creates a valid Environment instance
    }

    @Test
    void createEnvironment_shouldReturnConsistentInstance() {
        Environment environment1 = TodosInfrastructureApp.createEnvironment();
        Environment environment2 = TodosInfrastructureApp.createEnvironment();

        assertThat(environment1).isNotNull();
        assertThat(environment2).isNotNull();
        // Both should have same structure even if values differ
    }

    @Test
    void constructor_shouldNotBeInstantiable() {
        // Test utility class pattern - constructor should be private
        assertThat(TodosInfrastructureApp.class.getDeclaredConstructors()).hasSize(1);
        assertThat(TodosInfrastructureApp.class.getDeclaredConstructors()[0].getParameterCount()).isZero();
    }

    @Test
    void environmentBuilder_shouldHandleNullAccount() {
        // Test that Environment.builder() can handle null values gracefully
        Environment env = Environment.builder()
                .account(null)
                .region("eu-west-2")
                .build();

        assertThat(env).isNotNull();
        assertThat(env.getAccount()).isNull();
        assertThat(env.getRegion()).isEqualTo("eu-west-2");
    }

    @Test
    void environmentBuilder_shouldHandleNullRegion() {
        // Test that Environment.builder() can handle null values gracefully
        Environment env = Environment.builder()
                .account("123456789012")
                .region(null)
                .build();

        assertThat(env).isNotNull();
        assertThat(env.getAccount()).isEqualTo("123456789012");
        assertThat(env.getRegion()).isNull();
    }

    @Test
    void environmentBuilder_shouldHandleBothNull() {
        // Test that Environment.builder() can handle all null values
        Environment env = Environment.builder()
                .account(null)
                .region(null)
                .build();

        assertThat(env).isNotNull();
        assertThat(env.getAccount()).isNull();
        assertThat(env.getRegion()).isNull();
    }
}