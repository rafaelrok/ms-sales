FROM gradle:7.5.0-jdk11 AS build
COPY --chown=gradle:gradle . .
WORKDIR .
RUN gradle build -x test --no-daemon
EXPOSE 8085
CMD ["gradle", "bootRun"]