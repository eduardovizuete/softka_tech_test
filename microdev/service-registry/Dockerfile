FROM alpine/java:22-jdk
LABEL org.opencontainers.image.authors="vizuete.eduardo@gmail.com"
COPY target/service-registry-0.0.1-SNAPSHOT.jar service-registry-0.0.1-SNAPSHOT.jar
ENV TZ=America/Guayaquil
ENTRYPOINT ["java","-jar","/service-registry-0.0.1-SNAPSHOT.jar"]
