FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY blog-0.0.1.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]