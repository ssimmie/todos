package net.ssimmie.todos.infrastructure;

import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

import static org.assertj.core.api.Assertions.assertThat;

class EcsStackTest {

    @Test
    void constructor_shouldAcceptParameters() {
        App app = new App();
        StackProps stackProps = StackProps.builder()
                .env(Environment.builder()
                        .account("123456789012")
                        .region("eu-west-2")
                        .build())
                .build();

        // Create dependencies first
        NetworkStack networkStack = new NetworkStack(app, "TestNetwork", stackProps);

        // Test ECS stack creation with real dependencies
        EcsStack ecsStack = new EcsStack(app, "TestEcsStack", stackProps,
                networkStack.getVpc(),
                networkStack.getPrivateSecurityGroup(),
                "test-keyspace");

        assertThat(ecsStack).isNotNull();
        assertThat(ecsStack.getService()).isNotNull();
        assertThat(ecsStack.getEcrRepository()).isNotNull();
    }

    @Test
    void getService_shouldReturnConsistentInstance() {
        App app = new App();
        StackProps stackProps = StackProps.builder()
                .env(Environment.builder()
                        .account("123456789012")
                        .region("eu-west-2")
                        .build())
                .build();

        NetworkStack networkStack = new NetworkStack(app, "TestNetwork2", stackProps);
        EcsStack ecsStack = new EcsStack(app, "TestEcsStack2", stackProps,
                networkStack.getVpc(),
                networkStack.getPrivateSecurityGroup(),
                "test-keyspace");

        assertThat(ecsStack.getService()).isNotNull();
        assertThat(ecsStack.getService()).isSameAs(ecsStack.getService());
    }

    @Test
    void constructor_shouldHandleDifferentKeyspaceNames() {
        App app = new App();
        StackProps stackProps = StackProps.builder()
                .env(Environment.builder()
                        .account("123456789012")
                        .region("eu-west-2")
                        .build())
                .build();

        NetworkStack networkStack = new NetworkStack(app, "TestNetwork3", stackProps);

        EcsStack stack1 = new EcsStack(app, "EcsStack1", stackProps,
                networkStack.getVpc(),
                networkStack.getPrivateSecurityGroup(),
                "keyspace1");

        EcsStack stack2 = new EcsStack(app, "EcsStack2", stackProps,
                networkStack.getVpc(),
                networkStack.getPrivateSecurityGroup(),
                "keyspace2");

        assertThat(stack1.getService()).isNotNull();
        assertThat(stack2.getService()).isNotNull();
        assertThat(stack1.getService()).isNotSameAs(stack2.getService());
    }

    @Test
    void getEcrRepository_shouldReturnConsistentInstance() {
        App app = new App();
        StackProps stackProps = StackProps.builder()
                .env(Environment.builder()
                        .account("123456789012")
                        .region("eu-west-2")
                        .build())
                .build();

        NetworkStack networkStack = new NetworkStack(app, "TestNetwork4", stackProps);
        EcsStack ecsStack = new EcsStack(app, "TestEcsStack4", stackProps,
                networkStack.getVpc(),
                networkStack.getPrivateSecurityGroup(),
                "test-keyspace");

        assertThat(ecsStack.getEcrRepository()).isNotNull();
        assertThat(ecsStack.getEcrRepository()).isSameAs(ecsStack.getEcrRepository());
    }

    @Test
    void constructor_shouldCreateEcrRepositoryForEachStack() {
        App app = new App();
        StackProps stackProps = StackProps.builder()
                .env(Environment.builder()
                        .account("123456789012")
                        .region("eu-west-2")
                        .build())
                .build();

        NetworkStack networkStack = new NetworkStack(app, "TestNetwork5", stackProps);

        EcsStack stack1 = new EcsStack(app, "EcsStackEcr1", stackProps,
                networkStack.getVpc(),
                networkStack.getPrivateSecurityGroup(),
                "keyspace1");

        EcsStack stack2 = new EcsStack(app, "EcsStackEcr2", stackProps,
                networkStack.getVpc(),
                networkStack.getPrivateSecurityGroup(),
                "keyspace2");

        assertThat(stack1.getEcrRepository()).isNotNull();
        assertThat(stack2.getEcrRepository()).isNotNull();
        assertThat(stack1.getEcrRepository()).isNotSameAs(stack2.getEcrRepository());
    }
}