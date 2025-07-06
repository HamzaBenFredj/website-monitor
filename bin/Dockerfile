# Use official OpenJDK image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy source code into the container
COPY src /app/src

# Compile Java files
RUN find src -name "*.java" > sources.txt && \
    javac @sources.txt

# Run the main class and pass in a website URL from args
CMD ["java", "-cp", "src", "Models.WebsiteMonitor"]
