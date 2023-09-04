FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY build/libs/client-app-*.jar clientapp.jar
ENTRYPOINT ["java","-jar","/clientapp.jar"]