import socket

HOST = "0.0.0.0"  # Listen on all interfaces
PORT = 7001      # Port to listen on

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
                if data == "messageOfTheDay":
                    conn.sendall(b"You should not use plain text TCP when transmitting data.")
                elif data == "heartbeat":
                    conn.sendall(b"ok")
                elif data == "5604c261-651e-42fa-848e-31ab334e8f0c":
                    conn.sendall(b"Congratulation, you found a flag in the most unlikely place: dea0b6f6-3143-45fb-bd80-818ad6688797")
            except Exception as e:
                print(f"Error handling connection from {addr}: {e}")
            finally:
                conn.close()  # Explicitly close the connection
                print(f"Connection with {addr} closed")

if __name__ == "__main__":
    start_server()
