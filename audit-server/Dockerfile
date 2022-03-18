FROM openjdk:11.0.13-jre-slim-buster

EXPOSE 8084

ADD build/libs/audit-server.jar audit-server.jar

WORKDIR /KeyPair
ADD KeyPair/publicKey.txt publicKey.txt

ENTRYPOINT [ "java", "-jar", "/audit-server.jar", "--spring.profiles.active=deploy" ]