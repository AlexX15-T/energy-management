# energy-management

Project Name: Energy Management System Chat and Authorization Services
About

This project integrates chat and authorization microservices for an Energy Management System, focusing on secure, real-time communication and efficient notification handling. Built with Java Spring Boot and secured with JWTs, it features a React frontend, Docker for deployment, MySQL for data persistence, RabbitMQ for notifications, and WebSockets for real-time communication.
Functionality
Locally Running the Application

    Prerequisites: Ensure you have Java, Node.js, and MySQL installed on your local machine.
    Backend Setup: Navigate to the backend folder, and run mvn spring-boot:run to start the Spring Boot application.
    Frontend Setup: In the frontend directory, execute npm install followed by npm start to launch the React application.
    Database Configuration: Create a MySQL database named ems_db and run the provided SQL scripts to set up your tables.

Running with Docker

    Prerequisites: Docker and Docker Compose should be installed on your system.
    Docker Compose: Use the provided docker-compose.yml file to run the entire application stack with a single command: docker-compose up.
    Accessing the Application: Once the containers are up, access the application via http://localhost:3000 for the frontend and http://localhost:8080 for the backend services.

Demo

Watch a comprehensive video demonstration of the Energy Management System Chat and Authorization Services in action here. This video covers the project's main features, showcasing real-time user interaction, notification handling, and secure access mechanisms.
