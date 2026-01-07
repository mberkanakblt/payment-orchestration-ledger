FROM amazoncorretto:21.0.8-alpine3.22
WORKDIR /app
COPY target/Paycore-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]


