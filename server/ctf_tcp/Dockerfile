# Use an official Python runtime as a parent image
FROM python:3.9-slim

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container
COPY . /app

# Expose the port the app runs on
EXPOSE 12345

# Run the server
CMD ["python", "server.py"]