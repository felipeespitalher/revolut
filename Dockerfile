#Build stage
FROM gradle:jdk10 as builder

COPY --chown=gradle:gradle . .
RUN gradle build

#Runtime stage
FROM openjdk:8-jre-alpine

EXPOSE 8080

COPY --from=builder  /home/gradle/build/libs/revolut-1.0.0.jar /app/revolut.jar