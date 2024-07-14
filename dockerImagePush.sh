#!/bin/bash

# Check if a tag parameter is provided
if [ -z "$1" ]; then
    echo "Usage: $0 <tag>"
    exit 1
fi

# Get the tag from the first argument
tag=$1

# Define an array of images to push
images=(
    "iamsachintripathi/eurekaserver"
    "iamsachintripathi/configserver"
    "iamsachintripathi/gatewayserver"
    "iamsachintripathi/loans"
    "iamsachintripathi/cards"
    "iamsachintripathi/accounts"
)

# Loop through the images and push each one
for image in "${images[@]}"; do
    full_image_name="docker.io/$image:$tag"
    echo "Pushing $full_image_name..."
    docker image push "$full_image_name"
    if [ $? -eq 0 ]; then
        echo "$full_image_name pushed successfully!"
    else
        echo "Failed to push $full_image_name"
    fi
done

