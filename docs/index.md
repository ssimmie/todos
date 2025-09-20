---
layout: default
title: Home
nav_order: 1
---

# Todos Application

A modern, cloud-native todos and checklist management API built with Spring Boot 3.5.6, WebFlux, and Java 17. This application demonstrates best practices in reactive programming, hexagonal architecture, cloud-ready microservice development, and **Infrastructure as Code** with AWS CDK.

## 🚀 Key Features

- **Reactive API**: Built with Spring WebFlux for non-blocking, high-performance request handling
- **HATEOAS Compliance**: Fully RESTful API following Hypermedia as the Engine of Application State principles
- **Spring Native Ready**: Optimized for GraalVM native compilation with significantly faster startup times
- **Cloud-Native**: Docker-ready with comprehensive health checks and observability
- **AWS Infrastructure**: Complete Infrastructure as Code with AWS CDK in Java
- **Private & Secure**: VPC-only deployment with no public internet access
- **Hexagonal Architecture**: Clean separation of concerns with ports and adapters pattern
- **Comprehensive Testing**: Unit tests, integration tests, performance benchmarks, and 100% infrastructure test coverage
- **Production Ready**: Full CI/CD pipeline with quality gates and dependency scanning

## 🏗️ Architecture

The application follows **Hexagonal Architecture** (Ports & Adapters) patterns with full AWS cloud deployment:

### Application Architecture
- **Domain Layer**: Pure business logic with no external dependencies
- **Application Layer**: Use cases and application services
- **Infrastructure Layer**: Database adapters, web controllers, and external integrations
- **Reactive Stack**: Spring WebFlux + Project Reactor for non-blocking I/O

### AWS Infrastructure Architecture
- **Private VPC**: Isolated network with no public internet access
- **ECS Fargate**: Serverless container hosting for native images
- **Amazon Keyspaces**: Managed Cassandra-compatible database
- **Security Groups**: Restrictive network access controls
- **AWS CDK**: Infrastructure as Code written in Java

## 🛠️ Technology Stack

| Category | Technology | Version |
|----------|------------|---------|
| **Runtime** | Java | 17 |
| **Framework** | Spring Boot | 3.5.6 |
| **Web** | Spring WebFlux | Reactive |
| **Database** | Apache Cassandra / Keyspaces | 4.x |
| **Build** | Maven | 3.9.11 |
| **Testing** | JUnit 5 + Mockito | Latest |
| **Performance** | Gatling | Java SDK |
| **Containerization** | Docker + CNB | Native Image |
| **Infrastructure** | AWS CDK | 2.162.1 |
| **Cloud Platform** | AWS | ECS + Keyspaces |
| **Security** | Private VPC | No public access |

## 📊 Performance

With Spring Native compilation:
- **Startup Time**: ~50ms (vs 2-3s JVM)
- **Memory Usage**: ~64MB (vs 256MB+ JVM)
- **Container Size**: ~100MB (vs 200MB+ JVM)
- **API Response Time**: <5ms (95th percentile)
- **Throughput**: 90+ requests/second

## 🚦 Quick Start

### Prerequisites
- Java 17+
- Docker & Docker Compose
- Maven 3.9+

### Running the Application

1. **Clone and start dependencies**:
   ```bash
   git clone https://github.com/ssimmie/todos.git
   cd todos
   docker-compose -f config/docker-compose.yml up -d cassandra
   ```

2. **Build and run**:
   ```bash
   ./mvnw spring-boot:run -pl application
   ```

3. **Verify it's running**:
   ```bash
   curl http://localhost:8181/actuator/health
   ```

### Using Docker (Native Image)

1. **Build native image**:
   ```bash
   ./mvnw clean install  # Builds native image automatically
   ```

2. **Run with Docker Compose**:
   ```bash
   docker-compose -f config/docker-compose.yml up
   ```

### AWS Cloud Deployment 🚀

Deploy to AWS with complete Infrastructure as Code:

1. **Prerequisites**:
   ```bash
   npm install -g aws-cdk
   export CDK_DEFAULT_ACCOUNT=$(aws sts get-caller-identity --query Account --output text)
   export CDK_DEFAULT_REGION=us-east-1
   ```

2. **Deploy infrastructure**:
   ```bash
   ./mvnw clean install              # Build native image
   cd infrastructure
   cdk bootstrap                     # First time only
   cdk deploy --all                  # Deploy all stacks
   ```

3. **Access your private application**:
   - VPC-only deployment (no public access)
   - Connect via AWS Session Manager or VPN
   - See the [AWS Deployment Guide](deployment/) for details

**Features:**
- ✅ **Private & Secure**: No public internet access
- ✅ **Serverless**: ECS Fargate + Amazon Keyspaces
- ✅ **Infrastructure as Code**: AWS CDK in Java
- ✅ **Cost Optimized**: ~$30-85/month

## 📖 Documentation

- **[Architecture Overview](architecture/)** - System design and technical architecture
- **[API Reference](api/)** - Complete REST API documentation with examples
- **[Testing Guide](testing/)** - How to run tests and performance benchmarks
- **[AWS Deployment](deployment/)** - Infrastructure as Code and cloud deployment guide

## 🔧 Development

### Building the Project

```bash
# Full build with all tests and quality checks
./mvnw clean install

# Quick build
./mvnw clean install

# Native image build
./mvnw -pl application spring-boot:build-image \
  -Dspring-boot.build-image.env.BP_NATIVE_IMAGE=true
```

### Running Tests

```bash
# Unit tests
./mvnw test

# Integration tests (requires Docker)
./mvnw verify

# Performance tests with Gatling
./mvnw -pl performance-benchmark verify
```

### Code Quality

The project includes comprehensive quality checks:
- **Checkstyle** - Code formatting and style consistency
- **SpotBugs** - Static analysis for common bugs
- **PMD** - Code quality and best practices
- **JaCoCo** - Test coverage measurement
- **PIT** - Mutation testing for test effectiveness
- **FindSecBugs** - Security vulnerability scanning

## 🌐 API Overview

The API provides endpoints for managing todos and checklists:

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/` | GET | API root with hypermedia links |
| `/checklists` | GET, POST | Manage checklists collection |
| `/checklists/{id}` | GET | Individual checklist access |
| `/tasks` | GET, POST | Manage tasks/todos |
| `/actuator/health` | GET | Health check endpoint |

For complete API documentation with examples, see the [API Reference](api/).

## 📈 Monitoring & Observability

- **Health Checks**: Spring Boot Actuator endpoints
- **Metrics**: Application and JVM metrics
- **Tracing**: Ready for distributed tracing integration
- **Logging**: Structured logging with configurable levels

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Run tests (`./mvnw verify`)
4. Commit your changes (`git commit -m 'Add amazing feature'`)
5. Push to the branch (`git push origin feature/amazing-feature`)
6. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](https://github.com/ssimmie/todos/blob/master/LICENSE) file for details.

---

Built with ❤️ using Spring Boot, WebFlux, and modern Java practices.