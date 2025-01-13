# Kafka Book Project

This project demonstrates how to use **Spring Boot** with **Apache Kafka** to send book information to a Kafka topic and consume it. The project includes REST API endpoints to publish book data and leverages Kafka as the messaging platform.

## Features
- Publish book information to a Kafka topic via REST API.
- Kafka integration using Spring Boot.
- Configurable Kafka topic and server settings.

## Requirements
- Java 17 or later
- Maven
- Docker (for Kafka and Zookeeper)

---

## Setting Up Kafka Using Docker

1. Create a `docker-compose.yml` file to set up Kafka and Zookeeper:

   ```yaml
   version: '3.8'
   services:
     zookeeper:
       image: confluentinc/cp-zookeeper:latest
       container_name: zookeeper
       environment:
         ZOOKEEPER_CLIENT_PORT: 2181
       ports:
         - "2181:2181"

     kafka:
       image: confluentinc/cp-kafka:latest
       container_name: kafka
       ports:
         - "9092:9092"
       environment:
         KAFKA_BROKER_ID: 1
         KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
         KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
         KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
   ```

2. Start Kafka and Zookeeper:

   ```bash
   docker-compose up -d
   ```

3. Create the `book-topic`:

   ```bash
   docker exec -it kafka kafka-topics --create --topic book-topic --bootstrap-server localhost:9092
   ```

---

## Running the Spring Boot Application

1. Clone the repository or create a new Spring Boot project with the following structure:

   ```
   src/main/java/com/example/bookkafka
   ├── controller/
   ├── model/
   ├── service/
   ├── config/
   └── BookKafkaApplication.java
   ```

2. Add the following dependencies to your `pom.xml`:

   ```xml
   <dependencies>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.kafka</groupId>
           <artifactId>spring-kafka</artifactId>
       </dependency>
   </dependencies>
   ```

3. Create a `Dockerfile` to containerize the Spring Boot application:

   ```dockerfile
   FROM openjdk:17-jdk-slim
   VOLUME /tmp
   ARG JAR_FILE=target/*.jar
   COPY ${JAR_FILE} app.jar
   ENTRYPOINT ["java", "-jar", "/app.jar"]
   ```

4. Build and run the project:

   ```bash
   mvn clean package
   docker build -t kafka-book-app .
   docker run -p 8080:8080 kafka-book-app
   ```

---

## API Endpoints

### Publish Book Information

- **URL:** `/api/books/publish`
- **Method:** `POST`
- **Request Body:**

  ```json
  {
      "title": "Kafka in Action",
      "author": "John Doe",
      "year": 2021
  }
  ```
- **Response:**

  ```text
  Book sent to Kafka topic: book-topic
  ```

---

## Viewing Kafka Messages

Consume messages from the Kafka topic using:

```bash
kafka-console-consumer --topic book-topic --bootstrap-server localhost:9092 --from-beginning
```

---

## Folder Structure

```plaintext
src/main/java/com/example/bookkafka
├── config/                # Kafka configuration
├── controller/            # REST API controllers
├── model/                 # Data models
├── service/               # Kafka producer services
└── BookKafkaApplication.java  # Application entry point
```

---

## License
This project is open-source and available under the [MIT License](LICENSE).
