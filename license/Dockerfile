FROM openjdk:17-slim

LABEL maintainer="John Doe <a@a.com>"

ARG JAR_FILE

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
