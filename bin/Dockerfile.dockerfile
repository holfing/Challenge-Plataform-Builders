FROM openjdk:8
MAINTAINER RODRIGO TENORIO DE BOA VENTURA<boaventura19@yahoo.com.br>
ADD target/PlatformBuilders.jar	PlatformBuilders.jar
COPY	target/PlatformBuilders.jar	PlatformBuilders.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "PlatformBuilders.jar"]