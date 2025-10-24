FROM eclipse-temurin:17-jdk-alpine

RUN apk add --no-cache maven

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/lab01-1.0-SNAPSHOT.jar", "--server.port=8080", "--server.address=0.0.0.0"]