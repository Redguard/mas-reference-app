import asyncio
import ssl
import websockets

async def handle_client(websocket, path=None):
    async for message in websocket:
        print(f"Received: {message}")

        if "b7c4de22-2366-4ba3-946a-820a42a8e733" in message:
            response = "You authenticated properly, have a flag for the challenge \"Live update!\": 8b2b730f-958a-4e42-8879-6c76ffa9d37c"
        else:
            response = "Please show the passphrase to login."

        await websocket.send(response)

async def main():
    ssl_context = ssl.SSLContext(ssl.PROTOCOL_TLS_SERVER)
    ssl_context.load_cert_chain(certfile="/app/certs/fullchain1.pem", keyfile="/app/certs/privkey1.pem")

    async with websockets.serve(handle_client, "0.0.0.0", 6001, ssl=ssl_context):
        print("WebSocket server is running on wss://0.0.0.0:6001")
        await asyncio.Future()

if __name__ == "__main__":
    asyncio.run(main())
