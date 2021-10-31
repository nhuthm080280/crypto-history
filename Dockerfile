FROM openjdk:17
COPY build/libs/crypto-history-0.0.1-SNAPSHOT.jar crypto-history-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/crypto-history-0.0.1-SNAPSHOT.jar"]