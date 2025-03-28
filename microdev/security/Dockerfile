FROM alpine/java:22-jdk
LABEL org.opencontainers.image.authors="vizuete.eduardo@gmail.com"
COPY target/security-0.0.1-SNAPSHOT.jar security-0.0.1-SNAPSHOT.jar
ENV TZ=America/Guayaquil
ENTRYPOINT ["java","-jar","/security-0.0.1-SNAPSHOT.jar"]