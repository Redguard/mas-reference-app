docker compose up

docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ --dry-run -d  mas-reference-app.org

docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  mas-reference-app.org

docker compose up