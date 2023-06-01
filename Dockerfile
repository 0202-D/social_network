FROM openjdk:17
EXPOSE 8090
ADD target/social_network-0.0.1-SNAPSHOT.jar social_network.jar
ENTRYPOINT ["java", "-jar", "social_network.jar"]