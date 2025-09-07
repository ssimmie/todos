# Development Notes

## ErrorProne Reflective Access Warnings

When building with ErrorProne on Java 11, you may see warnings like:
```
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by com.google.errorprone.util.ErrorProneScope
```

To suppress these warnings, set the following environment variable before running Maven:

```bash
export MAVEN_OPTS="--add-opens=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.comp=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED --add-opens=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED"
```

Then run your Maven commands normally:
```bash
mvn clean compile
mvn test
```

## Project Structure

- **build-tools/**: Local module containing checkstyle configuration (eliminates external dependency)
- Updated ErrorProne to version 2.31.0 (latest compatible with Java 11)
- Upgraded OWASP dependency check to version 12.1.0