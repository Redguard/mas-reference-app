import socket

HOST = "0.0.0.0"
PORT = 7001

def start_server():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server_socket:
        server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        server_socket.bind((HOST, PORT))
        server_socket.listen(5)
        print(f"Server listening on {HOST}:{PORT}")

        while True:
            conn, addr = server_socket.accept()
            print(f"Connection from {addr}")
            try:
                data = conn.recv(1024).decode("utf-8").strip()
                print(data)
                if data == "HELP":
                    conn.sendall(b"ACK: Supported commands: INIT, TIP, HEARTBEAT, HELP")
                if data == "INIT":
                    conn.sendall(b"ACK: Hello client")
                if data == "TIP":
                    conn.sendall(b"You should not use plain text TCP when transmitting data.")
                elif data == "HEARTBEAT":
                    conn.sendall(b"ACK: dea0b6f6-3143-45fb-bd80-818ad6688797")
                elif data == "heartbeat":
                    conn.sendall(b"ERROR: Unknown command. Please see HELP.")
            except Exception as e:
                print(f"Error handling connection from {addr}: {e}")
            finally:
                conn.close()  # Explicitly close the connection
                print(f"Connection with {addr} closed")

if __name__ == "__main__":
    start_server()
