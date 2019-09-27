FROM openjdk:8
COPY ./target/file-0.0.1-SNAPSHOT-jar-with-dependencies.jar /usr/src/file/
WORKDIR /usr/src/file
EXPOSE 8080
CMD ["java", "-jar", "file-0.0.1-SNAPSHOT-jar-with-dependencies.jar"]