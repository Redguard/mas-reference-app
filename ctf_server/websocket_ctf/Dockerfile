# Use a lightweight Python image
FROM python:3.9-slim

# Set the working directory
WORKDIR /app

# Copy files
COPY server.py requirements.txt /app/

# Install dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Expose WebSocket port
EXPOSE 6001

# Run the server
CMD ["python", "server.py"]