FROM alpine/java:22-jdk
LABEL org.opencontainers.image.authors="vizuete.eduardo@gmail.com"
COPY target/micro_account_tx-0.0.1-SNAPSHOT.jar micro_account_tx-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/micro_account_tx-0.0.1-SNAPSHOT.jar"]