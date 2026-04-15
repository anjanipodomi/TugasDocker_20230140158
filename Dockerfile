FROM eclipse-temurin:25-jdk
ARG JAR_FILE=target/*.jar
COPY ./target/deploy6-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080