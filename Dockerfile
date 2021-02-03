FROM openjdk:8-jdk-alpine
ARG JAR_FILE
ADD ${JAR_FILE} /app/app.jar
EXPOSE 10010
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","/app/app.jar"]