# Introduction

This sub project sets up a small server the MAS Reference app can interact to. This is necessary to test the following use cases:

- Plain text HTTP, WebSocket, TCP and UDP-Traffic
- TLS pinning

The following table shows the different endpoints set up by this project:

| Endpoint                           | Description |
| ---------------------------------- | ------------------------------------------- |
| `http://<testDomain>`              |  Default HTTP                               |
| `https://<testDomain>`             |  Default HTTPS                              |
| `http://<testDomain>:3001`         |  HTTP on Non-Default Port                   |
| `https://invalidpin.<testDomain>`  |  Domain to test wrongly pinned certificates | 
| `ws://<testDomain>:2001`           |  WebSocket                                  |
| `<testDomain>:4001`                |  Raw UDP (echos the requests)               |
| `<testDomain>:5001`                |  Raw TPC (echos the requests)               |

For TLS we use Let's Encrypt and certbot for certificate enrollment.

# Getting Started


## Step 1: Set up your domain and update the nginx config file

Register your domain and replace the `<testDomain>` in the following files:

- `./nginx/conf_http/server_http.conf`
- `./nginx/conf_https/server_https.conf`


## Step 2: Start the Web-Server

Now start the nginx HTTP server as a daemon process:

```
docker compose up webserver_http -d 
```


## Step 3: Issue the TLS certificates

Use the certbot service to issue certificates for the 3 TLS endpoints:

```
docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  mas-reference-app.org
docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  nopin.mas-reference-app.org
docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  invalidpin.mas-reference-app.org
```

## Step 4: Start all servers

Now stop webserver and start all services again:

```
docker compose up -d
```


## Step 5: Update the domain in the MAS Reference app

Now you have to update the domain in the settings of the app. 

Congrats you are all set now. 
