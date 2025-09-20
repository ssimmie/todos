package net.ssimmie.todos.infrastructure;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.ISecurityGroup;
import software.amazon.awscdk.services.ec2.IVpc;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.FargateService;
import software.amazon.awscdk.services.ecs.FargateTaskDefinition;
import software.amazon.awscdk.services.ecs.LogDrivers;
import software.amazon.awscdk.services.ecs.Secret;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyStatement;
import software.amazon.awscdk.services.logs.LogGroup;
import software.amazon.awscdk.services.logs.RetentionDays;
import software.amazon.awscdk.services.secretsmanager.SecretStringGenerator;
import software.constructs.Construct;

import java.util.List;
import java.util.Map;

/**
 * ECS Fargate stack for running the Todos application.
 * Deploys the native image container in private subnets with secure configuration.
 */
public class EcsStack extends Stack {

    private final FargateService service;

    public EcsStack(final Construct scope, final String id, final StackProps props,
                   final IVpc vpc, final ISecurityGroup securityGroup, final String keyspaceName) {
        super(scope, id, props);

        Cluster cluster = createCluster(vpc);
        FargateTaskDefinition taskDefinition = createTaskDefinition(keyspaceName);
        this.service = createService(cluster, taskDefinition, securityGroup);

        // Output service ARN for reference
        CfnOutput.Builder.create(this, "ServiceArn")
                .value(service.getServiceArn())
                .description("ECS Service ARN for Todos application")
                .build();
    }

    /**
     * Creates ECS cluster for running containers.
     */
    private Cluster createCluster(final IVpc vpc) {
        return Cluster.Builder.create(this, "TodosCluster")
                .vpc(vpc)
                .clusterName("todos-cluster")
                .containerInsights(true)
                .build();
    }

    /**
     * Creates Fargate task definition with secure environment configuration.
     * No secrets hardcoded - all sensitive data via Secrets Manager.
     */
    private FargateTaskDefinition createTaskDefinition(final String keyspaceName) {
        FargateTaskDefinition taskDefinition = FargateTaskDefinition.Builder.create(this, "TodosTaskDefinition")
                .memoryLimitMiB(512)  // Native image requires less memory
                .cpu(256)             // Native image requires less CPU
                .build();

        // Grant Keyspaces access
        taskDefinition.addToTaskRolePolicy(PolicyStatement.Builder.create()
                .effect(Effect.ALLOW)
                .actions(List.of(
                        "cassandra:Select",
                        "cassandra:Insert",
                        "cassandra:Update",
                        "cassandra:Delete"
                ))
                .resources(List.of("*"))
                .build());

        // Create log group for application logs
        LogGroup logGroup = LogGroup.Builder.create(this, "TodosLogGroup")
                .logGroupName("/ecs/todos-application")
                .retention(RetentionDays.ONE_WEEK)
                .build();

        // Add container with secure environment variables
        taskDefinition.addContainer("TodosContainer", software.amazon.awscdk.services.ecs.ContainerDefinitionOptions.builder()
                .image(ContainerImage.fromRegistry("todos-application:native"))
                .environment(Map.of(
                        "SPRING_PROFILES_ACTIVE", "aws",
                        "SPRING_DATA_CASSANDRA_KEYSPACE_NAME", keyspaceName,
                        "SPRING_DATA_CASSANDRA_LOCAL_DATACENTER", "us-east-1",
                        "SPRING_DATA_CASSANDRA_CONTACT_POINTS", "cassandra.us-east-1.amazonaws.com",
                        "SPRING_DATA_CASSANDRA_PORT", "9142",
                        "SPRING_DATA_CASSANDRA_SSL", "true"
                ))
                .logging(LogDrivers.awsLogs(software.amazon.awscdk.services.ecs.AwsLogDriverProps.builder()
                        .logGroup(logGroup)
                        .streamPrefix("todos")
                        .build()))
                .healthCheck(software.amazon.awscdk.services.ecs.HealthCheck.builder()
                        .command(List.of("CMD-SHELL", "curl -f http://localhost:8181/actuator/health || exit 1"))
                        .timeout(Duration.seconds(5))
                        .interval(Duration.seconds(30))
                        .retries(3)
                        .build())
                .build());

        return taskDefinition;
    }

    /**
     * Creates Fargate service in private subnets only - no public access.
     */
    private FargateService createService(final Cluster cluster, final FargateTaskDefinition taskDefinition,
                                       final ISecurityGroup securityGroup) {
        return FargateService.Builder.create(this, "TodosService")
                .cluster(cluster)
                .taskDefinition(taskDefinition)
                .desiredCount(1)
                .assignPublicIp(false)  // Private subnets only
                .securityGroups(List.of(securityGroup))
                .serviceName("todos-service")
                .build();
    }

    public FargateService getService() {
        return service;
    }
}