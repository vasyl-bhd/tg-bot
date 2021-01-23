FROM adoptopenjdk:15-jre-hotspot
ARG JAR_FILE=target/lvivhotlinebot-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT exec java $JAVA_OPTS --enable-preview -jar /app.jar