FROM maven:3.8.7-openjdk-18-slim
ENV HOME=/app
RUN mkdir -p $HOME
WORKDIR $HOME
COPY settings.xml /root/.m2/settings.xml
COPY pom.xml $HOME

COPY src ./src
RUN ["mvn","package", "-DskipTests"]

ENTRYPOINT ["java", "-jar", "/app/target/tracc-0.0.1-SNAPSHOT.jar"]
