FROM ghcr.io/graalvm/graalvm-community:24 AS build
WORKDIR /app
COPY pom.xml .
COPY src src

# Copy Maven wrapper
COPY mvnw .
COPY .mvn .mvn

# Set execution permission for the Maven wrapper
RUN chmod +x ./mvnw
RUN ./mvnw clean -Pnative package



# Stage 2: Create the final Docker image using
FROM ghcr.io/graalvm/graalvm-community:24
VOLUME /tmp

# Copy the JAR from the build stage
COPY --from=build /app/target/promocode-image-generator app
RUN chmod +x ./app
ENTRYPOINT ["./app"]
EXPOSE 8080