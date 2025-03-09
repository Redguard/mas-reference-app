# Introduction

This sub project sets up a small server the MAS Reference app can interact to. This is necessary to test the following use cases:

- Plain text HTTP, WebSocket, TCP and UDP-Traffic
- TLS pinning

The following table shows the different endpoints set up by this project:

The MAS Reference App uses the following domains

| Endpoint                                    | Description                                 |
| ------------------------------------------- | ------------------------------------------- |
| `http://mas-reference-app.org`              |  Default HTTP                               |
| `https://mas-reference-app.org`             |  Default HTTPS                              |
| `http://mas-reference-app.org:3001`         |  HTTP on Non-Default Port                   |
| `https://invalidpin.mas-reference-app.org`  |  Domain to test wrongly pinned certificates | 
| `https://nopin.mas-reference-app.org`       |  Domain which is not pinned                 | 
| `ws://mas-reference-app.org:2001`           |  WebSocket                                  |
| `mas-reference-app.org:4001`                |  Raw UDP (echos the requests)               |
| `mas-reference-app.org:5001`                |  Raw TPC (echos the requests)               |


For the CTF we use the following domains:

| Endpoint                             | Description                                                    |   Flag | 
| ------------------------------------ | -------------------------------------------------------------- | ------- |
| `https://mas-reference-app.org`      |  CTFd with HTTP-Redirect                                       |    <none>     |
| `https://400e27f9-f9ff-4a86-8353-9c6df71a75b1.mas-reference-app.org/e364000e-75e7-4f05-9b0e-0690f1a14453.html`                                  |  Domain which is not pinned, the Flag is in the response.      | ce8cd9d6-e110-4f99-a395-1e2f7fca0522 |
| `https://f5bee9ca-1e4d-40b5-be3c-7000d57ed8e5.mas-reference-app.org/512c2349-2db6-48d7-be6f-b543277dc946.html`                     |  Domain which pinned using Android Manifest, the Flag is in the response.      | 7cdd4eaa-7d82-46ad-bd42-0439efb6cb52 | 
| `https://63b29c64-033c-44fd-bf95-d93b87c609da.mas-reference-app.org/308a0bcd-7bfc-4c7a-a8cd-78ed83f8b6a7.html`                                  |  Domain which programmatically pinned, custom Frida.re is required. the Flag is in the response.           | 2eb3c894-ea45-4d42-87fb-a8b7f71d7886 |
| `wss://mas-reference-app.org:6001`            |  WebSocket for the Scoreboard. The client provides b7c4de22-2366-4ba3-946a-820a42a8e733 | 8b2b730f-958a-4e42-8879-6c76ffa9d37c |
| `mas-reference-app.org:7001`         |  Raw TPC for a heartbeat. The client provides 5604c261-651e-42fa-848e-31ab334e8f0c     | dea0b6f6-3143-45fb-bd80-818ad6688797 |
| `https://1a5d77b3-5699-4ea9-862b-897451300045.mas-reference-app.org/308a0bcd-7bfc-4c7a-a8cd-78ed83f8b6a7.html`                                  |  This domain is never used in the app, but since its HTTPS the users can use Certificate Transparency to enumerate it          | 78bf6a45-31d2-4c96-a6fd-715b9ebc6167 |



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

docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  400e27f9-f9ff-4a86-8353-9c6df71a75b1.mas-reference-app.org
docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  f5bee9ca-1e4d-40b5-be3c-7000d57ed8e5.mas-reference-app.org
docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  63b29c64-033c-44fd-bf95-d93b87c609da.mas-reference-app.org
docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  1a5d77b3-5699-4ea9-862b-897451300045.mas-reference-app.org


```

## Step 4: Start all servers

Now stop webserver and start all services again:

```
docker compose up -d
```


## Step 5: Update the domain in the MAS Reference app

Now you have to update the domain in the settings of the app. 

Congrats you are all set now. 