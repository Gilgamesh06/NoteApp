# Stage 1: Builder

FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY Backend/NoteApp/  ./

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime

FROM eclipse-temurin:21-jdk

COPY --from=build  /app/target/*.jar ./app.jar

EXPOSE 8080 

ENTRYPOINT ["java", "-jar", "/app.jar" ]


