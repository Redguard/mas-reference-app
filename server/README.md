# Introduction

This sub project sets up a small server the MAS Reference app can interact to. This is necessary to test the following use cases:

- Plain text HTTP, WebSocket, TCP and UDP-Traffic
- TLS pinning

The following table shows the different endpoints set up by this project:

The MAS Reference App uses the following domains

| Endpoint                                    | Description                                 |
| ------------------------------------------- | ------------------------------------------- |
| `http://<test-domain>`              |  Default HTTP                               |
| `https://<test-domain>`             |  Default HTTPS                              |
| `http://<test-domain>:3001`         |  HTTP on Non-Default Port                   |
| `https://invalidpin.<test-domain>`  |  Domain to test wrongly pinned certificates | 
| `https://nopin.<test-domain>`       |  Domain which is not pinned                 | 
| `ws://<test-domain>:2001`           |  WebSocket                                  |
| `<test-domain>:4001`                |  Raw UDP (echos the requests)               |
| `<test-domain>:5001`                |  Raw TPC (echos the requests)               |


For the CTF we use the following domains:

| Endpoint                             | Description                                                    |   Flag | 
| ------------------------------------ | -------------------------------------------------------------- | ------- |
| `https://redguard-ctf.ch`      |  CTFd with HTTP-Redirect                                       |    <none>     |
| `https://scoreboard.<test-domain>/board/e364000e-75e7-4f05-9b0e-0690f1a14453`   |  Domain which is not pinned, the Flag is in the response. Domain used for top WV.     | ce8cd9d6-e110-4f99-a395-1e2f7fca0522 |
| `https://teams.<test-domain>/teams/308a0bcd-7bfc-4c7a-a8cd-78ed83f8b6a7`    |  Domain which programmatically pinned and in the Android Truststore, Frida.re is required, but there are off the shelf-scripts available. the Flag is in the response.   Domain used for bottom WV.        | 2eb3c894-ea45-4d42-87fb-a8b7f71d7886 |
| `wss://update.<test-domain>:6001`            |  WebSocket for the Scoreboard. The client provides b7c4de22-2366-4ba3-946a-820a42a8e733 | 8b2b730f-958a-4e42-8879-6c76ffa9d37c |
| `<test-domain>:7001`         |  Raw TPC for a heartbeat. The client provides 5604c261-651e-42fa-848e-31ab334e8f0c     | dea0b6f6-3143-45fb-bd80-818ad6688797 |
| `https://1a5d77b3-5699-4ea9-862b-897451300045.<test-domain>/index.html`   |  This domain is never used in the app, but since its HTTPS the users can use Certificate Transparency to enumerate it          | 78bf6a45-31d2-4c96-a6fd-715b9ebc6167 |
| `https://feedback.<test-domain>`  | Domain contacted to submit the feedback | 66FB159E-8477-4AB3-9029-173725448749 |



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

Use the certbot service to issue certificates for the various TLS endpoints:

```
docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  <test-domain>
docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  nopin.<test-domain>
docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  invalidpin.<test-domain>

docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  scoreboard.<test-domain>
docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  teams.<test-domain>
docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  update.<test-domain>
docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  1a5d77b3-5699-4ea9-862b-897451300045.<test-domain>
docker compose run --rm  certbot certonly --webroot --webroot-path /var/www/certbot/ -d  feedback.<test-domain>


```

## Step 4: Start all servers

Now stop webserver and start all services again:

```
docker compose up -d
```


## Step 5: Update the domain in the MAS Reference app

Now you have to update the domain in the settings of the app. 

Congrats you are all set now. 