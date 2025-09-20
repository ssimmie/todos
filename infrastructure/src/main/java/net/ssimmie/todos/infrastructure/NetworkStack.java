package net.ssimmie.todos.infrastructure;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.IVpc;
import software.amazon.awscdk.services.ec2.SecurityGroup;
import software.amazon.awscdk.services.ec2.SubnetConfiguration;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.constructs.Construct;

import java.util.List;

/**
 * Network infrastructure stack for Todos application.
 * Creates VPC with private subnets only - no public access for security.
 */
public class NetworkStack extends Stack {

    private final IVpc vpc;
    private final SecurityGroup privateSecurityGroup;

    public NetworkStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        this.vpc = createVpc();
        this.privateSecurityGroup = createPrivateSecurityGroup();
    }

    /**
     * Creates VPC with private subnets only for maximum security.
     * No public subnets = no internet gateway = no public access.
     */
    private IVpc createVpc() {
        return Vpc.Builder.create(this, "TodosVpc")
                .maxAzs(2)
                .subnetConfiguration(List.of(
                        SubnetConfiguration.builder()
                                .name("TodosPrivateSubnet")
                                .subnetType(SubnetType.PRIVATE_ISOLATED)
                                .cidrMask(24)
                                .build()
                ))
                .enableDnsHostnames(true)
                .enableDnsSupport(true)
                .build();
    }

    /**
     * Creates security group for private communication within VPC only.
     * No ingress rules from internet - only internal VPC communication.
     */
    private SecurityGroup createPrivateSecurityGroup() {
        SecurityGroup sg = SecurityGroup.Builder.create(this, "TodosPrivateSecurityGroup")
                .vpc(vpc)
                .description("Security group for Todos application - private access only")
                .allowAllOutbound(true)
                .build();

        // Allow internal communication within the security group
        sg.addIngressRule(sg, software.amazon.awscdk.services.ec2.Port.allTraffic(),
                "Allow internal communication within security group");

        return sg;
    }

    public IVpc getVpc() {
        return vpc;
    }

    public SecurityGroup getPrivateSecurityGroup() {
        return privateSecurityGroup;
    }
}