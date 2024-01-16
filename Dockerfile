FROM openjdk:17
COPY target/user-manager-service.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]