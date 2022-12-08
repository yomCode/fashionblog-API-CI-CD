FROM openjdk:11
ADD ./target/fashionBlog-0.0.1-SNAPSHOT.jar fashionBlog-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "fashionBlog-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080