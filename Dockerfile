FROM openjdk:22-slim
EXPOSE 8080
COPY target/rest-offers-*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]