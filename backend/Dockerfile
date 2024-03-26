ARG JAVA_VESRION=21

# Image to build all the backend services from source
FROM eclipse-temurin:${JAVA_VESRION}-jdk-alpine AS build
WORKDIR /app

COPY gradle/ ./gradle
COPY gradlew* ./
RUN ["./gradlew", "--no-daemon"]

COPY settings.gradle.kts ./
COPY build.gradle.kts ./
COPY config/build.gradle.kts ./config/
COPY gateway/build.gradle.kts ./gateway/
COPY registry/build.gradle.kts ./registry/
COPY users/build.gradle.kts ./users/
COPY messages/build.gradle.kts ./messages/
RUN ["./gradlew", "dependencies", "--no-daemon"]

COPY . .
RUN ["./gradlew", "bootJar", "--no-daemon"]


# Base image to run all the services
FROM eclipse-temurin:${JAVA_VESRION}-jre-alpine AS jre
RUN apk update
RUN apk add curl


# registry service
FROM jre AS registry
WORKDIR /app
COPY --from=build /app/registry/build/libs/registry-0.0.1-SNAPSHOT.jar registry.jar
ENTRYPOINT ["java", "-jar", "registry.jar"]


# config service
FROM jre AS config
WORKDIR /app
COPY --from=build /app/config/build/libs/config-0.0.1-SNAPSHOT.jar config.jar
ENTRYPOINT ["java", "-jar", "config.jar"]


# gateway service
FROM jre AS gateway
WORKDIR /app
COPY --from=build /app/gateway/build/libs/gateway-0.0.1-SNAPSHOT.jar gateway.jar
ENTRYPOINT ["java", "-jar", "gateway.jar"]


# users service
FROM jre AS users
WORKDIR /app
COPY --from=build /app/users/build/libs/users-0.0.1-SNAPSHOT.jar users.jar
ENTRYPOINT ["java", "-jar", "users.jar"]


# users service
FROM jre AS messages
WORKDIR /app
COPY --from=build /app/messages/build/libs/messages-0.0.1-SNAPSHOT.jar messages.jar
ENTRYPOINT ["java", "-jar", "messages.jar"]
