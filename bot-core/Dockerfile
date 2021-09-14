FROM openjdk:17-slim
ARG JAR_FILE=target/app.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar