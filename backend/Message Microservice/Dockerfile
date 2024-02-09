FROM openjdk:17
VOLUME /tmp
ADD target/messageMicroservice-0.0.1-SNAPSHOT.jar messageMicroservice.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/messageMicroservice.jar"]