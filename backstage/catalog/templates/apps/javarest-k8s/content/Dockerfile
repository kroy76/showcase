
FROM openjdk:17 as build
EXPOSE 8080
ADD target/restcrud.jar restcrud.jar
ENTRYPOINT ["java","-jar","restcrud.jar"]