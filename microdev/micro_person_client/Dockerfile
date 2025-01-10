FROM alpine/java:22-jdk
LABEL org.opencontainers.image.authors="vizuete.eduardo@gmail.com"
COPY target/micro_person_client-0.0.1-SNAPSHOT.jar micro_person_client-0.0.1-SNAPSHOT.jar
ENV TZ=America/Guayaquil
ENTRYPOINT ["java","-jar","/micro_person_client-0.0.1-SNAPSHOT.jar"]