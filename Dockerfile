#FROM eclipse-temurin:17-jdk-alpine
#VOLUME /tmp
#COPY target/*.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

# Unless explicitly stated otherwise all files in this repository are licensed
# under the Apache 2.0 License.
#
# This product includes software developed at Datadog (https://www.datadoghq.com/)
# Copyright 2023 Datadog, Inc.
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
