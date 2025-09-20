# Todos Infrastructure

AWS CDK infrastructure for the Todos application using Java.

## Overview

This module creates the complete AWS infrastructure needed to run the Todos application privately:

- **VPC**: Private isolated subnets only (no public access)
- **Amazon Keyspaces**: Managed Cassandra-compatible database
- **ECS Fargate**: Serverless container hosting for the native image
- **Security Groups**: Restrictive access controls

## Security Features

- ✅ **No public access**: Application runs in private subnets only
- ✅ **No hardcoded secrets**: All configuration via environment variables
- ✅ **Restrictive security groups**: Internal VPC communication only
- ✅ **Managed database**: Amazon Keyspaces handles security patches

## Prerequisites

1. **AWS CLI** configured with appropriate credentials
2. **AWS CDK** installed globally: `npm install -g aws-cdk`
3. **Docker** running (for native image container)
4. **Java 17+** and Maven

## Deployment

### 1. Set Environment Variables

```bash
export CDK_DEFAULT_ACCOUNT=$(aws sts get-caller-identity --query Account --output text)
export CDK_DEFAULT_REGION=us-east-1  # or your preferred region
```

### 2. Bootstrap CDK (first time only)

```bash
cd infrastructure
cdk bootstrap
```

### 3. Build Native Image

```bash
# From project root
./mvnw clean install  # This builds the native image
```

### 4. Deploy Infrastructure

```bash
cd infrastructure
cdk deploy --all
```

This will deploy three stacks:
- `TodosNetworkStack`: VPC and security groups
- `TodosKeyspacesStack`: Cassandra database
- `TodosEcsStack`: Application container service

### 5. Verify Deployment

```bash
# Check stack status
cdk list

# View stack outputs
aws cloudformation describe-stacks --stack-name TodosKeyspacesStack --query 'Stacks[0].Outputs'
```

## Configuration

The application will automatically connect to:
- **Keyspace**: `todos`
- **Tables**: `checklist`, `todo`
- **Endpoint**: `cassandra.{region}.amazonaws.com:9142`

## Accessing the Application

Since the application runs in private subnets, you'll need:

1. **VPN/Direct Connect** to your VPC, or
2. **Bastion host** in a public subnet, or
3. **AWS Systems Manager Session Manager** for secure access

Example using Session Manager:
```bash
# Find the ECS task
aws ecs list-tasks --cluster todos-cluster

# Connect to container
aws ecs execute-command \
  --cluster todos-cluster \
  --task YOUR_TASK_ARN \
  --container TodosContainer \
  --interactive \
  --command "/bin/sh"
```

## Cleanup

```bash
cd infrastructure
cdk destroy --all
```

## Development

### Running Tests

```bash
mvn test
```

### Code Coverage

The module maintains 100% test coverage as required by the build pipeline.

### Adding Resources

1. Add new constructs to appropriate stack classes
2. Add corresponding unit tests
3. Update this README if needed

## Cost Optimization

- **ECS Fargate**: Scales to zero when not in use
- **Keyspaces**: Pay-per-request pricing
- **VPC**: No NAT Gateway costs (private only)
- **Logs**: 1-week retention to minimize storage costs