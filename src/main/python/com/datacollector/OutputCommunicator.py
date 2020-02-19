import logging
import socket
from threading import Thread


class OutputCommunicator(Thread):
    def __init__(self, to_send, port, host):
        super().__init__()
        self._port = port
        self._to_send = to_send
        self._host = host

    def run(self):
        try:
            with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
                s.connect((self._host, self._port))
                data = str(self._to_send) + '\n'
                s.sendall(data.encode())
        except ConnectionError:
            logging.error('Failed to connect to server. Are you sure the server is up?')
