---
layout: default
title: Architecture
nav_order: 3
---

# Architecture Overview

The Todos application demonstrates modern cloud-native architecture patterns with complete Infrastructure as Code deployment.

## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    AWS Cloud Environment                   │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                   Private VPC                       │   │
│  │                 (No Public Access)                  │   │
│  │                                                     │   │
│  │  ┌─────────────────┐    ┌─────────────────┐       │   │
│  │  │  Private Subnet │    │  Private Subnet │       │   │
│  │  │    (AZ-1)       │    │    (AZ-2)       │       │   │
│  │  │                 │    │                 │       │   │
│  │  │  ┌───────────┐  │    │  ┌───────────┐  │       │   │
│  │  │  │ECS Fargate│  │    │  │ECS Fargate│  │       │   │
│  │  │  │           │  │    │  │ (Standby) │  │       │   │
│  │  │  │ ┌───────┐ │  │    │  │           │  │       │   │
│  │  │  │ │Native │ │  │    │  │           │  │       │   │
│  │  │  │ │Image  │ │  │    │  │           │  │       │   │
│  │  │  │ │       │ │  │    │  │           │  │       │   │
│  │  │  │ │Todos  │ │  │    │  │           │  │       │   │
│  │  │  │ │API    │ │  │    │  │           │  │       │   │
│  │  │  │ └───────┘ │  │    │  │           │  │       │   │
│  │  │  └───────────┘  │    │  └───────────┘  │       │   │
│  │  └─────────────────┘    └─────────────────┘       │   │
│  │                                                     │   │
│  │  ┌─────────────────────────────────────────────┐   │   │
│  │  │              Security Groups                │   │   │
│  │  │        (Internal Traffic Only)              │   │   │
│  │  └─────────────────────────────────────────────┘   │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │               Amazon Keyspaces                      │   │
│  │            (Managed Cassandra)                      │   │
│  │                                                     │   │
│  │  ┌─────────────┐    ┌─────────────┐                │   │
│  │  │ Checklist   │    │    Todo     │                │   │
│  │  │   Table     │    │   Table     │                │   │
│  │  │             │    │             │                │   │
│  │  │ - id (PK)   │    │ - checklist_id (PK)         │   │
│  │  │ - name      │    │ - id (CK)   │                │   │
│  │  │ - created   │    │ - task      │                │   │
│  │  │ - updated   │    │ - completed │                │   │
│  │  └─────────────┘    └─────────────┘                │   │
│  └─────────────────────────────────────────────────────┘   │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐   │
│  │                CloudWatch Logs                      │   │
│  │              (Application Logs)                     │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

## 🔧 Application Architecture

### Hexagonal Architecture (Ports & Adapters)

```
┌─────────────────────────────────────────────────────┐
│                  Application                        │
│                                                     │
│  ┌─────────────────────────────────────────────┐   │
│  │                Domain Layer                 │   │
│  │              (Pure Business Logic)          │   │
│  │                                             │   │
│  │  ┌─────────────┐    ┌─────────────────┐    │   │
│  │  │  Checklist  │    │      Todo       │    │   │
│  │  │   Entity    │    │     Entity      │    │   │
│  │  │             │    │                 │    │   │
│  │  │ - id        │    │ - id            │    │   │
│  │  │ - name      │    │ - task          │    │   │
│  │  │ - todos[]   │    │ - completed     │    │   │
│  │  └─────────────┘    └─────────────────┘    │   │
│  │                                             │   │
│  │  ┌─────────────────────────────────────┐   │   │
│  │  │           Value Objects             │   │   │
│  │  │                                     │   │   │
│  │  │ - ChecklistId                       │   │   │
│  │  │ - ChecklistName                     │   │   │
│  │  │ - TodoId                            │   │   │
│  │  └─────────────────────────────────────┘   │   │
│  └─────────────────────────────────────────────┘   │
│                                                     │
│  ┌─────────────────────────────────────────────┐   │
│  │             Application Layer               │   │
│  │              (Use Cases)                    │   │
│  │                                             │   │
│  │  ┌─────────────────────────────────────┐   │   │
│  │  │         Services (Ports)            │   │   │
│  │  │                                     │   │   │
│  │  │ - CreateChecklistService            │   │   │
│  │  │ - RetrieveChecklistService          │   │   │
│  │  │ - CreateTodoService                 │   │   │
│  │  │ - RetrieveTodoService               │   │   │
│  │  └─────────────────────────────────────┘   │   │
│  └─────────────────────────────────────────────┘   │
│                                                     │
│  ┌─────────────────────────────────────────────┐   │
│  │           Infrastructure Layer              │   │
│  │              (Adapters)                     │   │
│  │                                             │   │
│  │  ┌─────────────┐    ┌─────────────────┐    │   │
│  │  │     Web     │    │   Persistence   │    │   │
│  │  │   Adapter   │    │    Adapter      │    │   │
│  │  │             │    │                 │    │   │
│  │  │ REST API    │    │   Cassandra     │    │   │
│  │  │ WebFlux     │    │   Reactive      │    │   │
│  │  │ HATEOAS     │    │   Driver        │    │   │
│  │  └─────────────┘    └─────────────────┘    │   │
│  └─────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────┘
```

## 🌐 Network Architecture

### Security-First Design

```
┌─────────────────────────────────────────────────────┐
│                  AWS Region                         │
│                                                     │
│  ┌─────────────────────────────────────────────┐   │
│  │              Todos VPC                      │   │
│  │            10.0.0.0/16                      │   │
│  │                                             │   │
│  │  ┌─────────────────┐ ┌─────────────────┐   │   │
│  │  │ Private Subnet  │ │ Private Subnet  │   │   │
│  │  │  10.0.1.0/24    │ │  10.0.2.0/24    │   │   │
│  │  │     AZ-A        │ │     AZ-B        │   │   │
│  │  │                 │ │                 │   │   │
│  │  │  ┌───────────┐  │ │  ┌───────────┐  │   │   │
│  │  │  │    ECS    │  │ │  │    ECS    │  │   │   │
│  │  │  │   Task    │  │ │  │   Task    │  │   │   │
│  │  │  │(Primary)  │  │ │  │(Standby)  │  │   │   │
│  │  │  └───────────┘  │ │  └───────────┘  │   │   │
│  │  └─────────────────┘ └─────────────────┘   │   │
│  │                                             │   │
│  │  ┌─────────────────────────────────────┐   │   │
│  │  │          Security Group             │   │   │
│  │  │                                     │   │   │
│  │  │  Inbound Rules:                     │   │   │
│  │  │  ✅ Self-reference (all traffic)    │   │   │
│  │  │  ❌ No external ingress             │   │   │
│  │  │                                     │   │   │
│  │  │  Outbound Rules:                    │   │   │
│  │  │  ✅ All traffic (for AWS APIs)      │   │   │
│  │  └─────────────────────────────────────┘   │   │
│  │                                             │   │
│  │  Network Features:                          │   │
│  │  ❌ No Internet Gateway                     │   │
│  │  ❌ No NAT Gateway                          │   │   │
│  │  ❌ No Public Subnets                       │   │
│  │  ✅ Private DNS Resolution                  │   │
│  │  ✅ VPC Endpoints (AWS Services)            │   │
│  └─────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────┘
```

## 🗃️ Data Architecture

### Amazon Keyspaces Schema

```sql
-- Keyspace
CREATE KEYSPACE todos
WITH REPLICATION = {
  'class': 'SimpleStrategy',
  'replication_factor': 3
};

-- Checklist Table
CREATE TABLE todos.checklist (
    id text PRIMARY KEY,
    name text,
    created_at timestamp,
    updated_at timestamp
) WITH point_in_time_recovery = true;

-- Todo Table (Clustered by checklist)
CREATE TABLE todos.todo (
    checklist_id text,      -- Partition Key
    id text,                -- Clustering Key
    task text,
    completed boolean,
    created_at timestamp,
    updated_at timestamp,
    PRIMARY KEY (checklist_id, id)
) WITH CLUSTERING ORDER BY (id ASC)
AND point_in_time_recovery = true;
```

### Data Access Patterns

```
┌─────────────────────────────────────────────────────┐
│                Query Patterns                       │
│                                                     │
│  ┌─────────────────────────────────────────────┐   │
│  │              Checklist Queries              │   │
│  │                                             │   │
│  │  • GET /checklists                          │   │
│  │    → SCAN todos.checklist                   │   │
│  │                                             │   │
│  │  • GET /checklists/{id}                     │   │
│  │    → SELECT * FROM todos.checklist          │   │
│  │      WHERE id = {id}                        │   │
│  │                                             │   │
│  │  • POST /checklists                         │   │
│  │    → INSERT INTO todos.checklist            │   │
│  └─────────────────────────────────────────────┘   │
│                                                     │
│  ┌─────────────────────────────────────────────┐   │
│  │                Todo Queries                 │   │
│  │                                             │   │
│  │  • GET /tasks?checklist={id}                │   │
│  │    → SELECT * FROM todos.todo               │   │
│  │      WHERE checklist_id = {id}              │   │
│  │                                             │   │
│  │  • POST /tasks                              │   │
│  │    → INSERT INTO todos.todo                 │   │
│  │                                             │   │
│  │  • PUT /tasks/{id}                          │   │
│  │    → UPDATE todos.todo                      │   │
│  │      WHERE checklist_id = ? AND id = ?     │   │
│  └─────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────┘
```

## 🚀 Container Architecture

### Native Image Container

```
┌─────────────────────────────────────────────────────┐
│              Docker Container                       │
│           todos-application:native                  │
│                                                     │
│  ┌─────────────────────────────────────────────┐   │
│  │            Base Layer                       │   │
│  │        (Ubuntu Jammy Tiny)                  │   │
│  │             ~20MB                           │   │
│  └─────────────────────────────────────────────┘   │
│                                                     │
│  ┌─────────────────────────────────────────────┐   │
│  │          Native Executable                  │   │
│  │  net.ssimmie.todos.application.TodosApp    │   │
│  │             ~119MB                          │   │
│  │                                             │   │
│  │  Features:                                  │   │
│  │  ✅ Ahead-of-time compilation              │   │
│  │  ✅ No JVM overhead                        │   │
│  │  ✅ Fast startup (~50ms)                   │   │
│  │  ✅ Low memory usage (~64MB)               │   │
│  │  ✅ Optimized for cloud deployment         │   │
│  └─────────────────────────────────────────────┘   │
│                                                     │
│  ┌─────────────────────────────────────────────┐   │
│  │        Configuration Layer                  │   │
│  │                                             │   │
│  │  Environment Variables:                     │   │
│  │  • SPRING_PROFILES_ACTIVE=aws              │   │
│  │  • CASSANDRA_KEYSPACE=todos                │   │
│  │  • CASSANDRA_ENDPOINTS=keyspaces           │   │
│  │  • CASSANDRA_PORT=9142                     │   │
│  │  • CASSANDRA_SSL=true                      │   │
│  │                                             │   │
│  │  Health Check:                              │   │
│  │  • curl -f /actuator/health                │   │
│  │  • 30s interval, 5s timeout               │   │
│  └─────────────────────────────────────────────┘   │
│                                                     │
│  Total Container Size: ~237MB                       │
│  (vs ~520MB for JVM-based container)               │
└─────────────────────────────────────────────────────┘
```

## 🔒 Security Architecture

### Defense in Depth

```
┌─────────────────────────────────────────────────────┐
│                Security Layers                      │
│                                                     │
│  ┌─────────────────────────────────────────────┐   │
│  │            Network Security                 │   │
│  │                                             │   │
│  │  ✅ Private VPC (No public access)          │   │
│  │  ✅ Security Groups (Restrictive)           │   │
│  │  ✅ NACLs (Network ACL default)             │   │
│  │  ✅ No Internet Gateway                     │   │
│  │  ✅ No NAT Gateway                          │   │
│  └─────────────────────────────────────────────┘   │
│                                                     │
│  ┌─────────────────────────────────────────────┐   │
│  │         Application Security                │   │
│  │                                             │   │
│  │  ✅ No hardcoded secrets                    │   │
│  │  ✅ Environment-based config                │   │
│  │  ✅ Non-root container user                 │   │
│  │  ✅ Health check endpoints only             │   │
│  │  ✅ Minimal attack surface                  │   │
│  └─────────────────────────────────────────────┘   │
│                                                     │
│  ┌─────────────────────────────────────────────┐   │
│  │           Data Security                     │   │
│  │                                             │   │
│  │  ✅ Encryption at rest (Keyspaces)          │   │
│  │  ✅ Encryption in transit (TLS)             │   │
│  │  ✅ AWS IAM authentication                  │   │
│  │  ✅ Point-in-time recovery                  │   │
│  │  ✅ Automatic backups                       │   │
│  └─────────────────────────────────────────────┘   │
│                                                     │
│  ┌─────────────────────────────────────────────┐   │
│  │            IAM Security                     │   │
│  │                                             │   │
│  │  ✅ Least privilege principle               │   │
│  │  ✅ Task-specific roles                     │   │
│  │  ✅ No long-term credentials                │   │
│  │  ✅ AWS service authentication              │   │
│  │  ✅ Audit trail (CloudTrail)               │   │
│  └─────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────┘
```

## 📊 Performance Architecture

### Reactive Non-Blocking Design

```
┌─────────────────────────────────────────────────────┐
│              Request Flow                           │
│                                                     │
│  ┌─────────────────────────────────────────────┐   │
│  │               Load Balancer                 │   │
│  │            (ECS Service)                    │   │
│  └─────────────┬───────────────────────────────┘   │
│                │                                   │
│                ▼                                   │
│  ┌─────────────────────────────────────────────┐   │
│  │            Netty Server                     │   │
│  │         (Spring WebFlux)                    │   │
│  │                                             │   │
│  │  Event Loop Threads: 2-4                   │   │
│  │  Non-blocking I/O                           │   │
│  │  Reactive Streams                           │   │
│  └─────────────┬───────────────────────────────┘   │
│                │                                   │
│                ▼                                   │
│  ┌─────────────────────────────────────────────┐   │
│  │          WebFlux Router                     │   │
│  │                                             │   │
│  │  • Functional routing                       │   │
│  │  • Request validation                       │   │
│  │  • Content negotiation                      │   │
│  │  • HATEOAS links                           │   │
│  └─────────────┬───────────────────────────────┘   │
│                │                                   │
│                ▼                                   │
│  ┌─────────────────────────────────────────────┐   │
│  │         Application Services                │   │
│  │                                             │   │
│  │  • Reactive composition                     │   │
│  │  • Error handling                           │   │
│  │  • Business logic                           │   │
│  └─────────────┬───────────────────────────────┘   │
│                │                                   │
│                ▼                                   │
│  ┌─────────────────────────────────────────────┐   │
│  │       Cassandra Driver                     │   │
│  │                                             │   │
│  │  • Async/reactive driver                    │   │
│  │  • Connection pooling                       │   │
│  │  • Load balancing                           │   │
│  │  • Retry logic                              │   │
│  └─────────────┬───────────────────────────────┘   │
│                │                                   │
│                ▼                                   │
│  ┌─────────────────────────────────────────────┐   │
│  │          Amazon Keyspaces                   │   │
│  │                                             │   │
│  │  • Serverless scaling                       │   │
│  │  • Multi-AZ replication                     │   │
│  │  • Consistent performance                   │   │
│  └─────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────┘
```

### Performance Characteristics

| Metric | Native Image | JVM |
|--------|-------------|-----|
| **Startup Time** | ~50ms | 2-3s |
| **Memory Usage** | ~64MB | 256MB+ |
| **CPU Usage** | Lower at startup | Higher initially |
| **Container Size** | 237MB | 520MB+ |
| **First Request** | <10ms | 100ms+ |
| **Steady State** | <5ms | <5ms |

## 🔄 Deployment Architecture

### Infrastructure as Code Flow

```
┌─────────────────────────────────────────────────────┐
│              Developer Workflow                     │
│                                                     │
│  ┌─────────────────────────────────────────────┐   │
│  │           Local Development                 │   │
│  │                                             │   │
│  │  1. Code changes                            │   │
│  │  2. ./mvnw test                             │   │
│  │  3. ./mvnw clean install                    │   │
│  │  4. Native image build                      │   │
│  └─────────────┬───────────────────────────────┘   │
│                │                                   │
│                ▼                                   │
│  ┌─────────────────────────────────────────────┐   │
│  │        Infrastructure Deployment           │   │
│  │                                             │   │
│  │  1. cd infrastructure                       │   │
│  │  2. cdk diff                                │   │
│  │  3. cdk deploy --all                        │   │
│  │  4. Verify deployment                       │   │
│  └─────────────┬───────────────────────────────┘   │
│                │                                   │
│                ▼                                   │
│  ┌─────────────────────────────────────────────┐   │
│  │             AWS Services                    │   │
│  │                                             │   │
│  │  CloudFormation → Creates resources         │   │
│  │  ECR → Pulls container image               │   │
│  │  ECS → Deploys application                 │   │
│  │  Keyspaces → Database ready                │   │
│  └─────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────┘
```

---

## Next Steps

- **[AWS Deployment Guide](../deployment/)** - Deploy the infrastructure
- **[API Reference](../api/)** - Explore the REST API
- **[Testing Guide](../testing/)** - Run tests and benchmarks