FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

RUN mvn -q -DskipTests package

FROM eclipse-temurin:21-jre-alpine

ENV JAVA_OPTS=""

WORKDIR /app

COPY --from=builder /app/target/todoapp-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
