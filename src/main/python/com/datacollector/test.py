import socket
import time

HOST = '127.0.0.1'  # The server's hostname or IP address
PORT = 5000        # The port used by the server

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect((HOST, PORT))
        for i in range(1,10):
                data = 'Hello, ' + str(i) + '\n'
                s.sendall(data.encode())
                time.sleep(5)
