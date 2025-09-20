---
layout: default
title: AWS Deployment
nav_order: 4
---

# AWS Deployment Guide

Complete guide for deploying the Todos application to AWS using Infrastructure as Code with AWS CDK.

## 🏗️ Infrastructure Overview

The infrastructure creates a **completely private** AWS environment with:

- **Private VPC**: No public subnets or internet gateways
- **ECS Fargate**: Serverless container hosting
- **Amazon Keyspaces**: Managed Cassandra database
- **Security Groups**: Restrictive network controls
- **Zero public access**: Application accessible only within your AWS account

## 🔒 Security Architecture

```
┌─────────────────────────────────────────┐
│             Your AWS Account           │
│                                         │
│  ┌─────────────────────────────────┐   │
│  │         Private VPC             │   │
│  │                                 │   │
│  │  ┌─────────────┐ ┌───────────┐ │   │
│  │  │Private Subnet│ │Private Sub││   │
│  │  │             │ │           ││   │
│  │  │ ┌─────────┐ │ │           ││   │
│  │  │ │ECS Task │ │ │           ││   │
│  │  │ │Native   │ │ │           ││   │
│  │  │ │Image    │ │ │           ││   │
│  │  │ └─────────┘ │ │           ││   │
│  │  └─────────────┘ └───────────┘│   │
│  └─────────────────────────────────┘   │
│                                         │
│  ┌─────────────────────────────────┐   │
│  │       Amazon Keyspaces          │   │
│  │    (Managed Cassandra)          │   │
│  └─────────────────────────────────┘   │
│                                         │
└─────────────────────────────────────────┘
         No Public Internet Access
```

## 🚀 Quick Deployment

### Prerequisites

1. **AWS CLI** configured with appropriate permissions
2. **Node.js 18+** for AWS CDK
3. **Java 17+** and Maven
4. **Docker** for native image building

```bash
# Install AWS CDK
npm install -g aws-cdk

# Verify prerequisites
aws sts get-caller-identity
java --version
docker --version
```

### Step-by-Step Deployment

#### 1. Build Native Image

```bash
# From project root
./mvnw clean install

# This creates the native Docker image: todos-application:native
docker images | grep todos-application
```

#### 2. Configure Environment

```bash
# Set your AWS account and region
export CDK_DEFAULT_ACCOUNT=$(aws sts get-caller-identity --query Account --output text)
export CDK_DEFAULT_REGION=us-east-1  # or your preferred region

# Verify configuration
echo "Account: $CDK_DEFAULT_ACCOUNT"
echo "Region: $CDK_DEFAULT_REGION"
```

#### 3. Bootstrap CDK (First Time Only)

```bash
cd infrastructure
cdk bootstrap

# This creates the CDK staging bucket and IAM roles
```

#### 4. Deploy Infrastructure

```bash
# Deploy all stacks
cdk deploy --all

# Or deploy individually for debugging
cdk deploy TodosNetworkStack
cdk deploy TodosKeyspacesStack
cdk deploy TodosEcsStack
```

#### 5. Verify Deployment

```bash
# Check stack status
cdk list

# View outputs
aws cloudformation describe-stacks \
  --stack-name TodosKeyspacesStack \
  --query 'Stacks[0].Outputs'

# Check ECS service
aws ecs describe-services \
  --cluster todos-cluster \
  --services todos-service
```

## 📋 Infrastructure Components

### Network Stack (`TodosNetworkStack`)

Creates the secure network foundation:

```java
// VPC with private subnets only
Vpc.Builder.create(this, "TodosVpc")
    .maxAzs(2)
    .subnetConfiguration(List.of(
        SubnetConfiguration.builder()
            .name("TodosPrivateSubnet")
            .subnetType(SubnetType.PRIVATE_ISOLATED)  // No internet access
            .cidrMask(24)
            .build()
    ))
    .build();
```

**Security Features:**
- ✅ No public subnets
- ✅ No internet gateway
- ✅ No NAT gateway
- ✅ Private DNS resolution only

### Database Stack (`TodosKeyspacesStack`)

Creates managed Cassandra database:

```java
// Keyspace with tables matching domain model
CfnKeyspace keyspace = CfnKeyspace.Builder.create(this, "TodosKeyspace")
    .keyspaceName("todos")
    .build();

// Tables: checklist and todo
// Point-in-time recovery enabled
// No TTL (data persists)
```

**Features:**
- ✅ Serverless scaling
- ✅ Point-in-time recovery
- ✅ Encryption at rest
- ✅ AWS managed patches

### Application Stack (`TodosEcsStack`)

Deploys the native image container:

```java
// Fargate service in private subnets
FargateService.Builder.create(this, "TodosService")
    .cluster(cluster)
    .taskDefinition(taskDefinition)
    .assignPublicIp(false)  // Private only
    .securityGroups(List.of(securityGroup))
    .build();
```

**Configuration:**
- ✅ Native image deployment
- ✅ Health checks enabled
- ✅ CloudWatch logging
- ✅ IAM least privilege
- ✅ Environment variables (no secrets)

## 🔧 Access Patterns

Since the application runs in private subnets, access requires:

### Option 1: AWS Systems Manager Session Manager

```bash
# Find running task
TASK_ARN=$(aws ecs list-tasks \
  --cluster todos-cluster \
  --query 'taskArns[0]' \
  --output text)

# Connect to container
aws ecs execute-command \
  --cluster todos-cluster \
  --task $TASK_ARN \
  --container TodosContainer \
  --interactive \
  --command "/bin/sh"

# Test API from inside container
curl http://localhost:8181/actuator/health
```

### Option 2: VPC Peering/VPN

```bash
# If you have VPC connectivity:
curl http://PRIVATE_IP:8181/
```

### Option 3: Bastion Host (Not Recommended)

```bash
# Add a bastion host in public subnet (reduces security)
# SSH tunnel to access application
```

## 📊 Monitoring & Observability

### CloudWatch Integration

The deployment automatically configures:

```bash
# View application logs
aws logs describe-log-groups \
  --log-group-name-prefix "/ecs/todos"

# Tail logs in real-time
aws logs tail /ecs/todos-application --follow

# View metrics
aws cloudwatch get-metric-statistics \
  --namespace AWS/ECS \
  --metric-name CPUUtilization \
  --dimensions Name=ServiceName,Value=todos-service \
  --start-time 2024-01-01T00:00:00Z \
  --end-time 2024-01-01T23:59:59Z \
  --period 300 \
  --statistics Average
```

### Health Checks

```bash
# ECS health check endpoint
GET /actuator/health

# Response example:
{
  "status": "UP",
  "components": {
    "cassandra": {
      "status": "UP",
      "details": {
        "version": "4.0.0"
      }
    }
  }
}
```

## 💰 Cost Optimization

### Resource Costs (us-east-1)

| Service | Cost Model | Estimated Monthly |
|---------|------------|-------------------|
| **ECS Fargate** | $0.04048/hour (0.25 vCPU, 512MB) | ~$30 |
| **Keyspaces** | Pay-per-request | $0-50 (usage dependent) |
| **VPC** | No cost (private only) | $0 |
| **CloudWatch Logs** | $0.50/GB ingested | $1-5 |
| **Total** | | **$31-85/month** |

### Cost Reduction Strategies

```bash
# Scale service to zero when not needed
aws ecs update-service \
  --cluster todos-cluster \
  --service todos-service \
  --desired-count 0

# Scale back up
aws ecs update-service \
  --cluster todos-cluster \
  --service todos-service \
  --desired-count 1

# Delete log groups if not needed
aws logs delete-log-group \
  --log-group-name /ecs/todos-application
```

## 🧹 Cleanup

### Complete Removal

```bash
cd infrastructure

# Destroy all infrastructure
cdk destroy --all

# Confirm stacks are deleted
aws cloudformation list-stacks \
  --stack-status-filter DELETE_COMPLETE
```

### Selective Cleanup

```bash
# Remove application only (keep database)
cdk destroy TodosEcsStack

# Remove database only
cdk destroy TodosKeyspacesStack

# Remove network (must be last)
cdk destroy TodosNetworkStack
```

## 🔧 Troubleshooting

### Common Issues

#### 1. Task Not Starting

```bash
# Check ECS events
aws ecs describe-services \
  --cluster todos-cluster \
  --services todos-service \
  --query 'services[0].events[0:5]'

# Check task definition
aws ecs describe-task-definition \
  --task-definition todos-task
```

#### 2. Database Connection Issues

```bash
# Verify Keyspaces endpoint
nslookup cassandra.us-east-1.amazonaws.com

# Check IAM permissions
aws iam get-role --role-name TodosTaskRole
```

#### 3. Image Pull Issues

```bash
# Verify native image exists
docker images | grep todos-application:native

# Check ECR if using private registry
aws ecr describe-repositories
```

### Debug Commands

```bash
# CDK debugging
cdk diff TodosEcsStack
cdk synth --all > cdk-output.yaml

# CloudFormation events
aws cloudformation describe-stack-events \
  --stack-name TodosEcsStack \
  --query 'StackEvents[0:10]'

# ECS task logs
aws logs get-log-events \
  --log-group-name /ecs/todos-application \
  --log-stream-name "ecs/TodosContainer/TASK-ID"
```

## 🔐 Security Best Practices

### Implemented Security Measures

- ✅ **Network Isolation**: Private VPC with no internet access
- ✅ **Least Privilege IAM**: Minimal required permissions
- ✅ **No Hardcoded Secrets**: Environment-based configuration
- ✅ **Encryption**: Data encrypted at rest and in transit
- ✅ **Security Groups**: Restrictive network rules
- ✅ **Managed Services**: AWS handles security patches

### Additional Security Recommendations

```bash
# Enable VPC Flow Logs
aws ec2 create-flow-logs \
  --resource-type VPC \
  --resource-ids $VPC_ID \
  --traffic-type ALL \
  --log-destination-type cloud-watch-logs \
  --log-group-name VPCFlowLogs

# Enable AWS Config for compliance
aws configservice put-configuration-recorder \
  --configuration-recorder name=default \
  --recording-group allSupported=true

# Set up CloudTrail for audit logging
aws cloudtrail create-trail \
  --name todos-audit-trail \
  --s3-bucket-name your-audit-bucket
```

## 🚀 GitHub Actions CI/CD Pipeline

The project includes automated deployment via GitHub Actions that triggers on pushes to the master branch.

### Pipeline Overview

The CI/CD pipeline consists of two jobs:

1. **Build Job**: Compiles, tests, and builds the native image
2. **Deploy Job**: Deploys infrastructure to AWS using CDK

### AWS OIDC Authentication Setup

To enable secure deployment without long-term credentials, set up AWS OIDC:

#### 1. Create OIDC Identity Provider

```bash
# Create OIDC provider for GitHub Actions
aws iam create-open-id-connect-provider \
  --url https://token.actions.githubusercontent.com \
  --thumbprint-list 6938fd4d98bab03faadb97b34396831e3780aea1 \
  --client-id-list sts.amazonaws.com
```

#### 2. Create IAM Role for GitHub Actions

```bash
# Create trust policy for GitHub Actions
cat > github-actions-trust-policy.json << EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Federated": "arn:aws:iam::YOUR_ACCOUNT_ID:oidc-provider/token.actions.githubusercontent.com"
      },
      "Action": "sts:AssumeRoleWithWebIdentity",
      "Condition": {
        "StringEquals": {
          "token.actions.githubusercontent.com:aud": "sts.amazonaws.com",
          "token.actions.githubusercontent.com:sub": "repo:YOUR_USERNAME/todos:ref:refs/heads/master"
        }
      }
    }
  ]
}
EOF

# Create the IAM role
aws iam create-role \
  --role-name GitHubActionsTodosDeployment \
  --assume-role-policy-document file://github-actions-trust-policy.json
```

#### 3. Attach Required Permissions

```bash
# Attach necessary policies for CDK deployment
aws iam attach-role-policy \
  --role-name GitHubActionsTodosDeployment \
  --policy-arn arn:aws:iam::aws:policy/PowerUserAccess

# Note: For production, use more restrictive permissions
```

### GitHub Environment Configuration

Configure the following as GitHub repository variables and secrets:

#### Repository Variables

Navigate to **Settings > Environments > production** and add:

| Variable | Value | Description |
|----------|-------|-------------|
| `AWS_ACCOUNT_ID` | `123456789012` | Your AWS account ID |
| `AWS_REGION` | `us-east-1` | Target AWS region |
| `AWS_ROLE_ARN` | `arn:aws:iam::123456789012:role/GitHubActionsTodosDeployment` | IAM role ARN |

#### Environment Protection Rules

1. Go to **Settings > Environments > production**
2. Enable **Required reviewers** (optional)
3. Add **Deployment branches** rule for `master` branch only

### Pipeline Features

- ✅ **Secure Authentication**: No long-term AWS credentials stored
- ✅ **Environment Protection**: Requires approval for production deployments
- ✅ **Artifact Management**: Build artifacts passed between jobs
- ✅ **Verification**: Post-deployment health checks
- ✅ **Rollback Ready**: CDK diff shows changes before deployment

### Triggering Deployments

Deployments trigger automatically on pushes to master:

```bash
# Make changes and push to master
git add .
git commit -m "Update application"
git push origin master

# Monitor deployment in GitHub Actions tab
```

### Manual Deployment

You can also trigger deployments manually:

```bash
# From the GitHub Actions tab
# Select "ci-build" workflow
# Click "Run workflow" on master branch
```

### Monitoring Deployments

View deployment progress:

1. **GitHub Actions**: Real-time logs and status
2. **AWS CloudFormation**: Stack deployment events
3. **ECS Console**: Service health and task status
4. **CloudWatch**: Application logs and metrics

```bash
# Monitor via CLI
aws cloudformation describe-stack-events \
  --stack-name TodosEcsStack \
  --query 'StackEvents[0:10]'

# Check service status
aws ecs describe-services \
  --cluster todos-cluster \
  --services todos-service
```

---

## Next Steps

- **[API Reference](../api/)** - Test the deployed API
- **[Testing Guide](../testing/)** - Run integration tests against AWS
- **[GitHub Actions Setup](https://docs.github.com/en/actions/deployment/security-hardening-your-deployments/configuring-openid-connect-in-amazon-web-services)** - Additional OIDC configuration details
- **Main Documentation** - Return to [project overview](../)