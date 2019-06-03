FROM anapsix/alpine-java
LABEL maintainer="Nuwan Walisundara"
EXPOSE 8080
COPY target/n26-transactions-stats-*.jar n26-transactions-stats.jar
CMD java -jar $JAVA_OPTS n26-transactions-stats.jar