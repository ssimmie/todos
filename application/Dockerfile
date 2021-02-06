FROM openjdk:11
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} todos.jar
RUN apt-get update && apt-get install -y wait-for-it
ENTRYPOINT ["java","-jar","/todos.jar"]
EXPOSE 8181
