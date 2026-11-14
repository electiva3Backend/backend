#FROM maven:3-eclipse-temurin-25 AS builder
#
#WORKDIR /app
#
#COPY . .
#
#RUN mvn clean package -DskipTests -Dfile.encoding=UTF-8
#
#
#FROM eclipse-temurin:25-jre
#
#WORKDIR /app
#
#COPY --from=builder /app/target/*.jar app.jar
#
#EXPOSE 8083
#
#ENTRYPOINT ["java", "-jar", "app.jar"]

# ==========================================
# STAGE 1: BUILD
# ==========================================
FROM maven:3-eclipse-temurin-25 AS builder

WORKDIR /app

ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8

COPY . .

RUN mvn clean package -DskipTests -Dfile.encoding=UTF-8


# ==========================================
# STAGE 2: RUNTIME
# ==========================================
FROM eclipse-temurin:25-jre

WORKDIR /app

ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-XX:+UseSerialGC", "-XX:MaxRAMPercentage=65", "-XX:TieredStopAtLevel=1", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]