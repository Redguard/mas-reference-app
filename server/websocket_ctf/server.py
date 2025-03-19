import asyncio
import ssl
import websockets

async def handle_client(websocket, path=None):
    message_count = 0
    async for message in websocket:
        message_count += 1
        print(f"Received: {message}")

        try:
            if "b7c4de22-2366-4ba3-946a-820a42a8e733" in message:
                response = "You authenticated properly, have a flag for the challenge \"Live update!\": 8b2b730f-958a-4e42-8879-6c76ffa9d37c"
                await websocket.send(response)
                await websocket.close(code=1000)
                break
            else:
                response = "Please show the passphrase to login."
        except Exception as e:
            response = "An error occurred."

        await websocket.send(response)

        if message_count >= 2:
            await websocket.close(code=1000, reason="Session limit reached")
            break

async def main():
    ssl_context = ssl.SSLContext(ssl.PROTOCOL_TLS_SERVER)
    ssl_context.load_cert_chain(certfile="/app/certs/fullchain1.pem", keyfile="/app/certs/privkey1.pem")

    async with websockets.serve(handle_client, "0.0.0.0", 6001, ssl=ssl_context):
        print("WebSocket server is running on wss://0.0.0.0:6001")
        await asyncio.Future()

if __name__ == "__main__":
    asyncio.run(main())