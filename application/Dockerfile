FROM gcr.io/distroless/java:11
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} todos.jar
ENTRYPOINT ["java","-jar","/todos.jar"]
EXPOSE 8181
