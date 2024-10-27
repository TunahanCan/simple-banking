Running the Project with Docker
This guide explains how to build and run the project using Docker.


Prerequisites
Docker and Docker Compose installed on your system.
Docker Desktop running (if you're using Windows or macOS).
Steps to Build and Run the Project

1.Build the JAR File
   Before running the Docker container, you need to build the JAR file using Gradle. Open your terminal, navigate to the project root directory, and run:


./gradlew bootJar



2. Build and Run the Docker Container
   Use Docker Compose to build the image and run the container. Execute the following command in the project root directory:


docker-compose up --build


Application URL: http://localhost:1616
H2 Database Console: http://localhost:1616/h2-console
H2 Console Details:

JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (leave empty)

docker-compose down
This command will stop and remove the container.