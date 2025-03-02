FROM python:3.14-rc-alpine

WORKDIR /app

COPY . /app

RUN pip install --no-cache-dir -r requirements.txt

EXPOSE 8008

ENV FLASK_APP=app.py
ENV FLASK_RUN_HOST=0.0.0.0
ENV FLASK_RUN_PORT=8008

CMD ["flask", "run"]
#CMD ["gunicorn", "--bind", "0.0.0.0:8008", "app:app"]
