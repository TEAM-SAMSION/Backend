FROM amazoncorretto:17
ARG JAR_FILE=/Api-Module/build/libs/Api-Module-0.0.1.jar
COPY ${JAR_FILE} pawith.jar
ENTRYPOINT ["java","-Dcom.mysql.cj.disableAbandonedConnectionCleanup=true","-jar","/pawith.jar"]