FROM amazoncorretto:17
EXPOSE 8080
COPY build/libs/travel-0.0.1-SNAPSHOT.jar /app/spring-app.jar
ENTRYPOINT ["java","-jar","/app/spring-app.jar"]
