# Doctor's Clinic

[![Java Version](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) 
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-brightgreen.svg)](https://spring.io/projects/spring-boot)

Platform for managing a doctor's clinic. The doctor adds available slots for appointments, and patients book these slots. Confirmation notifications are sent to the patient and the doctor once an appointment is scheduled. The doctor can view booked appointments, and mark them as complete or cancelled.

Swagger: http://localhost:8080/swagger-ui/index.html

Prompt: [Modular Monolith Course Assement](https://docs.google.com/document/d/1G3y16cTEYC__xfKTnoHP3YrW0FQSRB2kf8S23i1Gg8M/edit?tab=t.0#heading=h.5uoc4mfz7mn4)

Example project: Charity Hub [https://github.com/TechMentors-LLC/charity_hub]

## Architecture

This platform is built using modular monolith architecture. Thus, this project is divided into the following modules:
* slots: allows the doctor to add available slots for appointments.
* booking: allows patients to book available slots.
* notifications: allows the system to send notifications to the patient and the doctor once an appointment is scheduled.
* appointments: allows the doctor to view booked appointments and mark them as complete or cancelled.

## Technologies
* Java 21
* Gradle
* Spring Boot 3.4
* PostgreSQL
* Docker Compose

## Run Locally

1. Start docker compose:
    ```bash
    docker-compose up
    ```
   
2. Run the Spring Boot application:
    ```bash
    ./gradlew bootRun
    ```

## Tests & Linting

* Linting:
    ```
   ./gradlew ktlintFormat
    ```

* Unit tests:
    ```
   ./gradlew test
    ```

* Code coverage:
    ```
   ./gradlew jacocoTestReport
    ```

* Integration tests:
    ```
   ./gradlew integrationTest
    ```

## Authors & Copyright
Shaimaa Sabry

[![GitHub](https://img.icons8.com/ios-glyphs/30/000000/github.png)](https://github.com/ShaimaaSabry)
[![LinkedIn](https://img.icons8.com/ios-filled/30/0A66C2/linkedin.png)](https://www.linkedin.com/in/shaimaa-sabry-161b71a0/)
