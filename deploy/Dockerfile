FROM amazoncorretto:11
ARG JAR_FILE=/Api-Module/build/libs/Api-Module-0.0.1.jar
COPY ${JAR_FILE} petmory.jar
ENTRYPOINT ["java","-jar","/petmory.jar"]