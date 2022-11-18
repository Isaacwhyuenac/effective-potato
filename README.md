# producer

## Project Setup

You will need the docker to setup the environment.

```zsh
docker-compose up -d
```

## Producer Side Contract Build

To run contract test

```zsh
./gradlew :order-service:contractTest
```

To build the contract for the consumer side to consume

```zsh
./gradlew :order-service:build publishToMavenLocal
```


