FROM eclipse-temurin:17-jdk-focal
ARG JAR_FILE=target/sem_1-1.0.0-SNAPSHOT.jar
ARG DB_HOST=5432
ARG DB_PORT=5432
ARG DB_USERNAME=postgres
ARG DB_PASSWORD=postgres
WORKDIR /app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
