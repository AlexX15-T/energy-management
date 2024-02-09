# energy-management

Project Name: Energy Management System Chat and Authorization Services

About: 
This project integrates chat and authorization microservices for an Energy Management System, focusing on secure, real-time communication and efficient notification handling. Built with Java Spring Boot and secured with JWTs, it features a React frontend, Docker for deployment, MySQL for data persistence, RabbitMQ for notifications, and WebSockets for real-time communication.

Pre-requirements

    Ensure you have Java, Node.js, and MySQL installed on your local machine.
    Java JDK 11 or later: For running Spring Boot applications.
    Node.js and npm: For managing the React frontend.
    Docker and Docker Compose: For containerization and orchestration, also RabbitMQ will run only on Docker.
    MySQL: For database services.

Functionality(I stronly recommand to choose the Docker option)
Locally Running the Application

    Backend Setup: Navigate to the backend folder, and run mvn clean install to generate target and after that run mvn spring-boot:run to start the Spring Boot application. 
    Do not forget to take care when choosing ports for each microservice in the backend.
    
    Frontend Setup: In the frontend directory, execute npm install followed by npm start to launch the React application.
    
    Database Configuration: Create a MySQL database for each microservice that are using: sd_user, sd_device, sd_consumer and sd_message.
    
    RabbitMQ Configuration: Docker installed is a requirement on this step. After this you can simply run:
        docker pull rabbitmq:3.10.5-management
        docker run --rm -it -p 15672:15672 -p 5672:5672 rabbitmq:3.10.5-management

    Accessing the Application: Once the servers are up, access the application via http://localhost:3000.

Running with Docker

    Prerequisites: Docker should be installed on your system. Use command mvn clean install in each backend microservice to have up-to-date targets.

    Docker Build: You can build the image for each microservice and run from Docker app or you can continue to run with the following step.
    command: docker build --no-cache -t device-microservice . 
    where device-microservice is the name of the current microservice.
    
    Docker Compose: Use the provided docker-compose.yml file from each microservice using commands like this: 
    command: docker-compose up -d --build    

    Docker Run: 
    Backend Microservices:  docker-compose up -d --build
    Frontend: docker run -p 8080:3000 energy-management
    
    Accessing the Application: Once the containers are up, access the application via http://localhost:3000 for the frontend and http://localhost:80xx for the backend services from postman or other application made for testing endpoints.

Diagrams:
Deployment:
![diagarma deployment](https://github.com/AlexX15-T/energy-management/assets/79551420/f663f714-729e-4463-ba8e-90fa093d9b0b)

Architecture:
![diagrama arhitectura](https://github.com/AlexX15-T/energy-management/assets/79551420/3b98d4fd-4e66-4b2d-8a25-f6372f57169b)


Demo

Watch a comprehensive video demonstration of the Energy Management System Chat and Authorization Services in action here. This video covers the project's main features, showcasing real-time user interaction, notification handling, and secure access mechanisms.



https://github.com/AlexX15-T/energy-management/assets/79551420/3de17bcc-591f-428e-9517-f15cc4392012




