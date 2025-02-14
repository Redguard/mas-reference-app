docker compose up webserver_http

// then create certs

docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ --dry-run -d  mas-reference-app.org

docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  mas-reference-app.org
docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  invalidpin.mas-reference-app.org



// then start both http and https server
docker compose up



Docs:

http://<testDomain>                 // Default HTTP (Port 80)
https://<testDomain>                // Default HTTPS (Port 443)
https://invalidpin.<testDomain>     // Domain to test wrongly pinned certificates (Port 443)

ws://<testDomain>:2001              // WebSocket (Port 2001)

http://<testDomain>:3001            // HTTP on Non-Default Port

<testDomain>:4001                   // Raw UDP (echos the requests)
<testDomain>:5001                   // Raw TPC (echos the requests)


