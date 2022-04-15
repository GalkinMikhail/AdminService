FROM openjdk:17-oracle
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} adminServiceApp.jar
ENTRYPOINT ["java", "-jar", "adminServiceApp.jar"]