#!/bin/bash

# List of microservice directories
MICROSERVICES=("accounts" "cards" "configserver" "eurekaserver" "gatewayserver" "loans")

# Loop through each microservice directory
for service in "${MICROSERVICES[@]}"
do
    echo "Building Docker image for $service..."
    
    # Navigate into the microservice directory
    cd "$service"
    
    # Execute Maven command to build Docker image using Jib plugin
    ./mvnw compile jib:dockerBuild
    
    # Navigate back to the root directory
    cd ..
done

echo "All Docker images built successfully."

