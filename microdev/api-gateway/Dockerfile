FROM alpine/java:22-jdk
LABEL org.opencontainers.image.authors="vizuete.eduardo@gmail.com"
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway-0.0.1-SNAPSHOT.jar
ENV TZ=America/Guayaquil
ENTRYPOINT ["java","-jar","/api-gateway-0.0.1-SNAPSHOT.jar"]
