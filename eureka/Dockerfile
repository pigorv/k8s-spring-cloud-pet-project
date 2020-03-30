FROM openjdk:11-jdk-slim
ARG REVISION
COPY target/eureka-${REVISION}.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]