FROM openjdk:11.0.13-jre-slim-buster

EXPOSE 8082

ADD target/movie-server.jar movie-server.jar

WORKDIR /KeyPair
ADD KeyPair/publicKey.txt publicKey.txt

# ENTRYPOINT [ "java", "-jar", "/movie-server.jar" ]
ENTRYPOINT [ "java", "-jar", "/movie-server.jar", "--spring.profiles.active=deploy" ]