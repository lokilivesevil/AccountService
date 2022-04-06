FROM openjdk:11
WORKDIR /usr/src/myapp
COPY target/accountservice-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "accountservice-0.0.1-SNAPSHOT.jar", "&"]
EXPOSE 8080