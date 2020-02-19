from observable import Observable


class FifoQueue(Observable):
    _queue = []

    def enqueue(self, data):
        self._queue.append(data)
        self.trigger("toSendQueue", self._queue)

    def dequeue(self):
        if self._queue.__sizeof__() > 0:
            return self._queue.pop()
        else:
            return None
