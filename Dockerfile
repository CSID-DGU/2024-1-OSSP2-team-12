FROM eclipse-temurin:17-jdk
VOLUME /tmp
COPY target/*.jar app.jar
RUN mkdir -p temp
ENTRYPOINT ["java", "-jar", "/app.jar"]