import argparse
import socket
import sys
import os

path = os.path.dirname(os.path.realpath(__file__)) + "/../.."
sys.path.append(path)

from com.datacollector.DataCollector import DataCollector
from com.datacollector.FSWriter import FSWriter
from com.datacollector.OutputCommunicator import OutputCommunicator

data_collector = DataCollector()
data_collector.start()

parser = argparse.ArgumentParser("python3 Application.py")
parser.add_argument("--port", nargs='?', type=int, default=5000, required=False,
                    help="the port to send the data to (default: 5000)")
parser.add_argument("--host", nargs='?', type=str, default="127.0.0.1", required=False,
                    help="the ip address to send the data to (default: 127.0.0.1)")
parser.add_argument("--file", nargs='?', type=str, default="/var/log/javaapp/" + str(socket.gethostname() + "/data.log"),
                    required=False,
                    help="the file where to log the data (default: /var/log/$HOSTNAME/data.log")

args = parser.parse_args()


@DataCollector.on(data_collector, "new_data")
def send_handler(data):
    output_thread = OutputCommunicator(data, args.port, args.host)
    output_thread.start()
    file_system_thread = FSWriter(args.file, data)
    file_system_thread.start()
