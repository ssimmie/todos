package net.ssimmie.todos.infrastructure;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

/**
 * CDK Application entry point for Todos infrastructure.
 * This application creates all AWS resources needed to run the Todos application privately.
 */
public final class TodosInfrastructureApp {

    private TodosInfrastructureApp() {
        // Utility class
    }

    public static void main(final String[] args) {
        App app = new App();

        Environment env = createEnvironment();
        StackProps stackProps = StackProps.builder()
                .env(env)
                .build();

        // Create network infrastructure (VPC, subnets, security groups) - private only
        NetworkStack networkStack = new NetworkStack(app, "TodosNetworkStack", stackProps);

        // Create Keyspaces database
        KeyspacesStack keyspacesStack = new KeyspacesStack(app, "TodosKeyspacesStack", stackProps);

        // Create ECS service for application (private subnets only)
        EcsStack ecsStack = new EcsStack(app, "TodosEcsStack", stackProps,
                networkStack.getVpc(),
                networkStack.getPrivateSecurityGroup(),
                keyspacesStack.getKeyspaceName());

        ecsStack.addDependency(networkStack);
        ecsStack.addDependency(keyspacesStack);

        app.synth();
    }

    /**
     * Creates environment configuration from system properties or environment variables.
     * No hardcoded account/region values to prevent secrets in code.
     */
    static Environment createEnvironment() {
        return Environment.builder()
                .account(System.getProperty("cdk.account", System.getenv("CDK_DEFAULT_ACCOUNT")))
                .region(System.getProperty("cdk.region", System.getenv("CDK_DEFAULT_REGION")))
                .build();
    }
}