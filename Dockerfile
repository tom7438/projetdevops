FROM ubuntu

RUN apt update && apt install openjdk-17-jdk -y && apt install maven -y && mkdir -p /src

COPY ./src/ /src/
COPY ./pom.xml /

RUN mvn clean && mvn compile

CMD java -classpath /target/classes/ DockerScenario