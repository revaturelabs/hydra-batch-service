FROM openjdk:8-jdk-alpine
ADD . /BatchRepositoryService
WORKDIR /BatchRepositoryService
CMD ["java", "-jar", "target/BatchRepositoryService-1.jar"]