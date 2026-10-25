FROM maven:3-eclipse-temurin-25 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests -Dfile.encoding=UTF-8


FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
