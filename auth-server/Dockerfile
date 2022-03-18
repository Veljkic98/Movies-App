FROM openjdk:11.0.13-jre-slim-buster

EXPOSE 8081

ADD target/auth-server.jar auth-server.jar

WORKDIR /KeyPair
ADD KeyPair/privateKey.txt privateKey.txt

# ENTRYPOINT [ "java", "-jar", "/auth-server.jar" ]
ENTRYPOINT [ "java", "-jar", "/auth-server.jar", "--spring.profiles.active=deploy" ]