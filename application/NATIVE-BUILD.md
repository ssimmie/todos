# Spring Native Build Instructions

This application has been configured for GraalVM Native Image support for faster startup times and reduced memory usage.

## Expected Benefits

- **Startup Time**: ~50-90% reduction (2-5s → 50-200ms)
- **Memory Usage**: ~60-80% reduction (512MB → 128MB)
- **Container Size**: Smaller images, faster deployments

## Prerequisites

### Option 1: Install GraalVM locally
```bash
# Download GraalVM JDK 17
curl -L https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-22.3.0/graalvm-ce-java17-linux-amd64-22.3.0.tar.gz -o graalvm.tar.gz
tar -xzf graalvm.tar.gz
export JAVA_HOME=$PWD/graalvm-ce-java17-22.3.0
export PATH=$JAVA_HOME/bin:$PATH

# Install native-image component
gu install native-image
```

### Option 2: Use Docker (Recommended)
No local GraalVM installation needed - uses container-based building.

## Build Commands

**⚡ Native images are now built by default!** Simply run:

```bash
# Builds both JVM JAR and native Docker image
./mvnw clean package
```

### Legacy Commands (for reference)

### 1. AOT Processing (Test Configuration)
```bash
./mvnw -pl application spring-boot:process-aot
```

### 2. Native Image Build (Local GraalVM)
```bash
./mvnw -pl application -Pnative native:compile
```

### 3. Manual Native Container Build
```bash
./mvnw -pl application spring-boot:build-image
```

### 4. Run Native Application
```bash
# Run native container (default)
docker run --rm -p 8181:8181 todos-application:native

# Or run JVM version if needed
docker run --rm -p 8181:8181 docker.pkg.github.com/ssimmie/artifact-repository/todos-application:latest
```

### Switching Back to JVM for Testing (if needed)

```bash
# Use JVM images for acceptance tests
./mvnw -pl acceptance-tests verify -Ddocker.compose.file=docker-compose.yml

# Use JVM images for performance tests
./mvnw -pl performance-benchmark verify -Ddocker.compose.file=docker-compose.yml
```

## Configuration

The application includes:

- ✅ Native build profile configuration
- ✅ GraalVM buildtools plugin
- ✅ Spring Boot native image support
- ✅ Cassandra driver reflection hints
- ✅ Resource configuration for Cassandra
- ✅ HATEOAS AOT processing

## Compatibility Status

| Component | Native Support | Status |
|-----------|----------------|--------|
| Spring Boot 3.5.5 | ✅ Full | Ready |
| WebFlux | ✅ Full | Ready |
| Spring Data Cassandra | ✅ 2025.1.0+ | Ready |
| HATEOAS | ✅ Full | Ready |
| Actuator | ✅ Full | Ready |
| Micrometer | ✅ Full | Ready |

## Troubleshooting

### Common Issues

1. **Missing Reflection Configuration**
   - Add entries to `reflect-config.json`
   - Use `@RegisterForReflection` annotation

2. **Resource Loading Issues**
   - Update `resource-config.json`
   - Ensure resources are in classpath

3. **Cassandra Connection Issues**
   - Verify native-image hints for driver classes
   - Check connection configuration

### Performance Tuning

```bash
# Build with memory optimizations
./mvnw -pl application -Pnative native:compile \
  -Dspring-boot.build-image.env.BP_NATIVE_IMAGE_BUILD_ARGUMENTS="--gc=G1 --initialize-at-build-time=org.slf4j"
```

## Monitoring Native Build

The build process shows:
- AOT processing of Spring components
- Proxy generation for controllers
- HATEOAS type registration
- Reflection hints registration

Build time: ~2-5 minutes (first build), ~30s-1min (incremental)