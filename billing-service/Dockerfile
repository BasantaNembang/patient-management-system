# -------- Stage 1: Build --------
FROM maven:3.9.5-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests


FROM gcr.io/distroless/java17-debian11:latest

WORKDIR /

COPY --from=builder /app/target/*.jar /app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app.jar", "--server.port=8081"]


