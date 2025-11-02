package net.ssimmie.todos.infrastructure;

import java.util.List;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.GatewayVpcEndpointAwsService;
import software.amazon.awscdk.services.ec2.GatewayVpcEndpointOptions;
import software.amazon.awscdk.services.ec2.IVpc;
import software.amazon.awscdk.services.ec2.InterfaceVpcEndpointAwsService;
import software.amazon.awscdk.services.ec2.InterfaceVpcEndpointOptions;
import software.amazon.awscdk.services.ec2.Port;
import software.amazon.awscdk.services.ec2.SecurityGroup;
import software.amazon.awscdk.services.ec2.SubnetConfiguration;
import software.amazon.awscdk.services.ec2.SubnetSelection;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.constructs.Construct;

/**
 * Network infrastructure stack for Todos application. Creates VPC with private subnets only - no
 * public access for security. Uses VPC endpoints instead of NAT Gateway for cost-effective AWS
 * service access.
 */
public class NetworkStack extends Stack {

  private final IVpc vpc;
  private final SecurityGroup privateSecurityGroup;
  private final SecurityGroup vpcEndpointSecurityGroup;

  /**
   * Creates a new NetworkStack with private-only networking infrastructure and VPC endpoints.
   *
   * @param scope the parent construct
   * @param id the construct ID
   * @param props the stack properties
   */
  public NetworkStack(final Construct scope, final String id, final StackProps props) {
    super(scope, id, props);

    this.vpc = createVpc();
    this.privateSecurityGroup = createPrivateSecurityGroup();
    this.vpcEndpointSecurityGroup = createVpcEndpointSecurityGroup();

    // Create VPC endpoints for AWS services (no NAT Gateway needed)
    createS3GatewayEndpoint();
    createEcrEndpoints();
    createCloudWatchLogsEndpoint();
    createKeyspacesEndpoint();
  }

  /**
   * Creates VPC with private subnets only for maximum security. No public subnets = no internet
   * gateway = no public access.
   */
  private IVpc createVpc() {
    return Vpc.Builder.create(this, "TodosVpc")
        .maxAzs(2)
        .subnetConfiguration(
            List.of(
                SubnetConfiguration.builder()
                    .name("TodosPrivateSubnet")
                    .subnetType(SubnetType.PRIVATE_ISOLATED)
                    .cidrMask(24)
                    .build()))
        .enableDnsHostnames(true)
        .enableDnsSupport(true)
        .build();
  }

  /**
   * Creates security group for private communication within VPC only. No ingress rules from
   * internet - only internal VPC communication.
   */
  private SecurityGroup createPrivateSecurityGroup() {
    SecurityGroup sg =
        SecurityGroup.Builder.create(this, "TodosPrivateSecurityGroup")
            .vpc(vpc)
            .description("Security group for Todos application - private access only")
            .allowAllOutbound(true)
            .build();

    // Allow internal communication within the security group
    sg.addIngressRule(sg, Port.allTraffic(), "Allow internal communication within security group");

    return sg;
  }

  /**
   * Creates security group for VPC endpoints. Allows HTTPS traffic from application security group.
   */
  private SecurityGroup createVpcEndpointSecurityGroup() {
    SecurityGroup sg =
        SecurityGroup.Builder.create(this, "VpcEndpointSecurityGroup")
            .vpc(vpc)
            .description("Security group for VPC endpoints")
            .allowAllOutbound(false)
            .build();

    // Allow HTTPS from application security group to VPC endpoints (ECR, CloudWatch, etc.)
    sg.addIngressRule(privateSecurityGroup, Port.tcp(443), "Allow HTTPS from application");

    // Allow Keyspaces port from application (Cassandra uses port 9142 with TLS)
    sg.addIngressRule(privateSecurityGroup, Port.tcp(9142), "Allow Cassandra from application");

    return sg;
  }

  /** Creates S3 Gateway endpoint for ECR image layers (no cost, no hourly charge). */
  private void createS3GatewayEndpoint() {
    vpc.addGatewayEndpoint(
        "S3GatewayEndpoint",
        GatewayVpcEndpointOptions.builder()
            .service(GatewayVpcEndpointAwsService.S3)
            .subnets(
                List.of(SubnetSelection.builder().subnetType(SubnetType.PRIVATE_ISOLATED).build()))
            .build());
  }

  /** Creates ECR VPC endpoints for Docker registry access without NAT Gateway. */
  private void createEcrEndpoints() {
    // ECR API endpoint - for Docker client API calls
    vpc.addInterfaceEndpoint(
        "EcrApiEndpoint",
        InterfaceVpcEndpointOptions.builder()
            .service(InterfaceVpcEndpointAwsService.ECR)
            .subnets(SubnetSelection.builder().subnetType(SubnetType.PRIVATE_ISOLATED).build())
            .securityGroups(List.of(vpcEndpointSecurityGroup))
            .privateDnsEnabled(true)
            .build());

    // ECR Docker endpoint - for pulling Docker images
    vpc.addInterfaceEndpoint(
        "EcrDockerEndpoint",
        InterfaceVpcEndpointOptions.builder()
            .service(InterfaceVpcEndpointAwsService.ECR_DOCKER)
            .subnets(SubnetSelection.builder().subnetType(SubnetType.PRIVATE_ISOLATED).build())
            .securityGroups(List.of(vpcEndpointSecurityGroup))
            .privateDnsEnabled(true)
            .build());
  }

  /** Creates CloudWatch Logs endpoint for application logging without NAT Gateway. */
  private void createCloudWatchLogsEndpoint() {
    vpc.addInterfaceEndpoint(
        "CloudWatchLogsEndpoint",
        InterfaceVpcEndpointOptions.builder()
            .service(InterfaceVpcEndpointAwsService.CLOUDWATCH_LOGS)
            .subnets(SubnetSelection.builder().subnetType(SubnetType.PRIVATE_ISOLATED).build())
            .securityGroups(List.of(vpcEndpointSecurityGroup))
            .privateDnsEnabled(true)
            .build());
  }

  /**
   * Creates Keyspaces (Cassandra) endpoint for database access without NAT Gateway. Keyspaces
   * requires HTTPS (port 9142 uses TLS).
   */
  private void createKeyspacesEndpoint() {
    vpc.addInterfaceEndpoint(
        "KeyspacesEndpoint",
        InterfaceVpcEndpointOptions.builder()
            .service(InterfaceVpcEndpointAwsService.KEYSPACES)
            .subnets(SubnetSelection.builder().subnetType(SubnetType.PRIVATE_ISOLATED).build())
            .securityGroups(List.of(vpcEndpointSecurityGroup))
            .privateDnsEnabled(true)
            .build());
  }

  public IVpc getVpc() {
    return vpc;
  }

  public SecurityGroup getPrivateSecurityGroup() {
    return privateSecurityGroup;
  }

  public SecurityGroup getVpcEndpointSecurityGroup() {
    return vpcEndpointSecurityGroup;
  }
}
