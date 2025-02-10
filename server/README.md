docker compose up webserver_http

// then create certs

docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ --dry-run -d  mas-reference-app.org

docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  mas-reference-app.org
docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  invalidpin.mas-reference-app.org



// then start both http and https server
docker compose up