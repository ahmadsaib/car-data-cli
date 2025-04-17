#!/bin/bash

# Arguments with defaults
FILTER=$1
SORT=$2
OUTPUT=$3

# Run Docker container with provided arguments
echo "🚀 Running the application..."
docker run --rm \
  -v $(pwd)/CarsBrand.csv:/app/CarsBrand.csv \
  -v $(pwd)/carsType.xml:/app/carsType.xml \
  -v $(pwd)/logs:/app/logs \
  car-data-cli \
  -csv /app/CarsBrand.csv \
  -xml /app/carsType.xml \
  -filter "$FILTER" \
  -sort "$SORT" \
  -output "$OUTPUT"