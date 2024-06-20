FROM openjdk:17
WORKDIR /app

ARG JAR_FILE

COPY ${JAR_FILE} app.jar
COPY roadmapp.db roadmapp.db

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]