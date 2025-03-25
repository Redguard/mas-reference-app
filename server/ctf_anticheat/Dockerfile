FROM python:3.11-alpine

WORKDIR /app

COPY . /app

RUN apk add gcc musl-dev python3-dev libffi-dev pkgconfig

RUN pip install --no-cache-dir -r requirements.txt

EXPOSE 8008

#CMD ["flask", "--debug", "run", "--cert=adhoc"]
CMD gunicorn --certfile /app/certs/live/anticheat.<test-domain>/fullchain.pem --keyfile /app/certs/live/anticheat.<test-domain>/privkey.pem --bind 0.0.0.0:8008 app:app