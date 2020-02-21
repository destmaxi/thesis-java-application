import random
import time
from threading import Thread

from observable import Observable


class DataCollector(Observable, Thread):

    def __init__(self):
        Observable.__init__(self)
        Thread.__init__(self)
        self._observers = []

    def run(self):
        while True:
            data = self.collect()
            self.trigger("new_data", data=data)
            time.sleep(2)

    def collect(self):
        return random.randint(0, 100)
