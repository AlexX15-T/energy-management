FROM openjdk:17
VOLUME /tmp
ADD target/spring-boot-login-example-0.0.1-SNAPSHOT.jar userMicroservice.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/userMicroservice.jar"]