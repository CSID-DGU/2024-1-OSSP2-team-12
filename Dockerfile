FROM eclipse-temurin:17-jdk
VOLUME /home/tomas040788/temp
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]