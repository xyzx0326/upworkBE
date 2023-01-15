FROM openjdk:17-jdk-alpine
MAINTAINER baeldung.com
COPY target/northwind-0.0.1-SNAPSHOT.jar northwind-0.0.1-SNAPSHOT.jar
RUN mkdir -p "/app/NeMo/tools/asr_webapp/uploads/"
EXPOSE 5432
ENTRYPOINT ["java","-jar","/northwind-0.0.1-SNAPSHOT.jar"]