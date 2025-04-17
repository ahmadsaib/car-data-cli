#!/bin/bash

# Always build the JAR
echo "📦 Building the project with Maven..."
mvn clean package

# Always rebuild Docker image
echo "🐳 Building Docker image..."
docker build -t car-data-cli .