FROM openjdk:11.0.13-jre-slim-buster

EXPOSE 8083

ADD target/report-server.jar report-server.jar

WORKDIR /KeyPair
ADD KeyPair/publicKey.txt publicKey.txt

WORKDIR /app

ENTRYPOINT [ "java", "-jar", "/report-server.jar", "--spring.profiles.active=deploy" ]