
FROM amazoncorretto:17 as build
LABEL maintainer='Diogo André Botas'

WORKDIR /workspace/app
COPY . /workspace/app
RUN ./gradlew build --no-daemon

FROM amazoncorretto:17 as production
WORKDIR /app
VOLUME /tmp
COPY --from=build /workspace/app/build/libs/music-notifier-web-api.jar /app/
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/music-notifier-web-api.jar"]
