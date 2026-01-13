# EventShuffle Application

This project is a Spring Boot application backed by a PostgreSQL database and packaged using Docker. The application is built with Gradle and can be started easily using Docker Compose.

---

## Prerequisites

Make sure you have the following installed on your system:

* **Docker**
* **Docker Compose**
---

---

## Configuration Overview

### Application stack

* **Spring Boot**
* **Gralde**
* **Jooq**
* **Flyway**
* **PostgreSQL 17**

The application connects to the database using the following environment variables (configured in `docker-compose.yml`):

```
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/eventshuffle_db
SPRING_DATASOURCE_USERNAME=demo_user
SPRING_DATASOURCE_PASSWORD=demo_password
```

---

## How to Start the Application

### 1. Clone the Repository

```
git clone git@github.com:williamirva/eventshuffle.git
```

### 2. Build and Start with Docker Compose

From the root directory of the project (where `docker-compose.yml` is located), run:

```
docker compose up --build
```

This command will:

1. Build the Spring Boot application using Gradle
2. Create a Docker image for the application
3. Start a PostgreSQL container
4. Start the application container and connect it to the database

---

## Accessing the Application

Once everything has started successfully:

* **Application URL:** [http://localhost:8080](http://localhost:8080)
* **PostgreSQL:** available on `localhost:5432`
---

## Testing the endpoints

From test.http can be found http calls to each endpoint in EventController. First ID in the sequence is 1.

## Running unit and integration tests

To run tests locally you need to have Java 25 installed locally.
Integration tests to pass make sure that database is running in docker.

In terminal run: ./gradlew test

## Stopping the Application

To stop the application and database, press `CTRL + C` in the terminal, then run:

```
docker compose down
```

To also remove the database data volume:

```
docker compose down -v
```

⚠️ This will permanently delete all PostgreSQL data.

---
---

## Notes

* Flyway is included for database migrations (configured via Gradle).
* The application JAR is built inside the Docker image, so no local build is required.
* Ports can be adjusted in `docker-compose.yml` if needed.

---
