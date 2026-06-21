FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /workspace

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline

COPY src/ src/
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S wordfinder && adduser -S wordfinder -G wordfinder

WORKDIR /app
COPY --from=build /workspace/target/wordfinder-*.jar app.jar

USER wordfinder
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
