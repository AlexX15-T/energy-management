FROM openjdk:17
VOLUME /tmp
ADD target/consumer-0.0.1-SNAPSHOT.jar consumer.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/consumer.jar"]