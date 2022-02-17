FROM openjdk:17-jdk-slim-buster
WORKDIR /app
EXPOSE 8080
ADD . /myapp
WORKDIR /myapp
ENTRYPOINT ["java","-jar","target/credit-0.0.1-SNAPSHOT.jar"]