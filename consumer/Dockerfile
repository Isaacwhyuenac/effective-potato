FROM gradle:7.5.1-jdk11

WORKDIR /usr/home

COPY . .

RUN ./gradlew build && java -jar build/libs/consumer-0.0.1-SNAPSHOT.jar
