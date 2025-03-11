FROM python:3.14-rc-alpine

WORKDIR /app

COPY . /app

# Required for the `cryptography` library
RUN apk add gcc musl-dev python3-dev libffi-dev openssl-dev cargo pkgconfig

RUN pip install --no-cache-dir -r requirements.txt

EXPOSE 8008

#CMD ["flask", "--debug", "run", "--cert=adhoc"]
CMD gunicorn --certfile certs/fullchain.pem --keyfile certs/privkey.pem --bind 0.0.0.0:8008 app:app
