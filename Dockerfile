FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/*.jar /app/app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
