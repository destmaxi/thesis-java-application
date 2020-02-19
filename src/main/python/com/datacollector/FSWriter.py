import errno
import os
from threading import Thread


class FSWriter(Thread):
    def __init__(self, file_path, data):
        super().__init__()
        self._file_path = file_path
        self._data = data

    def run(self):
        print("FSWriter: " + str(self._data))
        if not os.path.exists(os.path.dirname(self._file_path)):
            try:
                os.makedirs(os.path.dirname(self._file_path))
            except OSError as exc:
                if exc.errno != errno.EEXIST:
                    raise

        file = open(self._file_path, 'a')
        file.write(str(self._data) + '\n')
        file.close()
