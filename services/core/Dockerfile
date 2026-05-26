FROM maven:3.9.12-eclipse-temurin-21-alpine AS builder

WORKDIR /shared

#COPY shared/proto-gen-java ./proto-gen-java
#
#RUN mvn clean install -DskipTests -f proto-gen-java/pom.xml

COPY shared/common-java ./common-java

RUN mvn clean install -DskipTests -f common-java/pom.xml

WORKDIR /app

COPY services/core .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine-3.23

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 9000

ENTRYPOINT [ "java", "-jar","app.jar"]
