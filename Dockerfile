FROM openjdk:17-ea-jdk-buster

RUN apt-get update -y; apt-get install curl -y
RUN apt-get -y install maven

WORKDIR /talan

#Uncomment to add the datadog tracing library

#RUN curl -Lo dd-java-agent.jar https://dtdg.co/latest-java-tracer

COPY . cdz/
WORKDIR /talan/cdz

#Uncomment to run without tracing
#ENTRYPOINT ["java", "-jar", "target/notes-0.0.1-SNAPSHOT.jar"]

#Uncomment to run with tracing
ENTRYPOINT ["java", "-javaagent:dd-java-agent.jar","-Ddd.trace.sample.rate=1", "-jar", "target/TalanCDZ-0.0.1-SNAPSHOT.jar"]
