Running the Project with Docker
This guide explains how to build and run the project using Docker. Follow these simple steps to get the application up and running.

Prerequisites
Docker and Docker Compose installed on your system.
Docker Desktop running (if you're using Windows or macOS).
Steps to Build and Run the Project
1. Build the JAR File
   Before running the Docker container, you need to build the JAR file using Gradle. Open your terminal, navigate to the project root directory, and run:

bash
Kodu kopyala
./gradlew bootJar
This command will generate the JAR file in the build/libs directory.

2. Build and Run the Docker Container
   Use Docker Compose to build the image and run the container. Execute the following command in the project root directory:

bash
Kodu kopyala
docker-compose up --build
This command will:

Build the Docker image for the application.
Start the application inside a Docker container.
3. Access the Application
   Once the Docker container is running, access the application via:

Application URL: http://localhost:1616
H2 Database Console: http://localhost:1616/h2-console
H2 Console Details:

JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (leave empty)
4. Stopping the Application
   To stop the Docker container, you can press CTRL + C in the terminal where it is running. Alternatively, you can run:

bash
Kodu kopyala
docker-compose down
This command will stop and remove the container.