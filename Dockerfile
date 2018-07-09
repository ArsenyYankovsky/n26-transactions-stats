FROM openjdk:8-jre-alpine
LABEL maintainer="Ankur Malik"
EXPOSE 8080
COPY target/statistics*.jar statistics.jar
CMD java -jar $JAVA_OPTS statistics.jar