FROM adoptopenjdk/openjdk11:alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} exploringmars.jar
ENTRYPOINT ["java","-jar","/exploringmars.jar"]
