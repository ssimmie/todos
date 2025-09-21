package net.ssimmie.todos.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.IVpc;
import software.amazon.awscdk.services.ec2.SecurityGroup;

class NetworkStackTest {

  @Test
  void constructor_shouldCreateStackWithBasicComponents() {
    App app = new App();
    StackProps stackProps =
        StackProps.builder()
            .env(Environment.builder().account("123456789012").region("eu-west-2").build())
            .build();

    NetworkStack networkStack = new NetworkStack(app, "TestNetworkStack", stackProps);

    assertThat(networkStack).isNotNull();
    assertThat(networkStack.getVpc()).isNotNull();
    assertThat(networkStack.getPrivateSecurityGroup()).isNotNull();
  }

  @Test
  void getVpc_shouldReturnConsistentInstance() {
    App app = new App();
    StackProps stackProps =
        StackProps.builder()
            .env(Environment.builder().account("123456789012").region("eu-west-2").build())
            .build();

    NetworkStack networkStack = new NetworkStack(app, "TestNetworkStack2", stackProps);
    IVpc vpc1 = networkStack.getVpc();
    IVpc vpc2 = networkStack.getVpc();

    assertThat(vpc1).isNotNull();
    assertThat(vpc2).isNotNull();
    assertThat(vpc1).isSameAs(vpc2);
  }

  @Test
  void getPrivateSecurityGroup_shouldReturnConsistentInstance() {
    App app = new App();
    StackProps stackProps =
        StackProps.builder()
            .env(Environment.builder().account("123456789012").region("eu-west-2").build())
            .build();

    NetworkStack networkStack = new NetworkStack(app, "TestNetworkStack3", stackProps);
    SecurityGroup sg1 = networkStack.getPrivateSecurityGroup();
    SecurityGroup sg2 = networkStack.getPrivateSecurityGroup();

    assertThat(sg1).isNotNull();
    assertThat(sg2).isNotNull();
    assertThat(sg1).isSameAs(sg2);
  }
}
