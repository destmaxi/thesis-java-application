package com.ibrdtnapplication;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Logger;

class FifoQueue<T> extends Observable<FifoQueue<T>> {
    private static final Logger log = Logger.getLogger("FifoQueue");
    private ConcurrentLinkedDeque<T> values = new ConcurrentLinkedDeque<>();

    FifoQueue() {
        this("FifoBundleQueue");
    }

    private FifoQueue(String name) {
        super(name);
    }

    synchronized void enqueue(T value) {
        log.info("notified");
        if (value == null) return;
        boolean ret = this.values.add(value);
        log.info("ret: " + ret);
        if (ret) {
            update(this);
        }
    }

    synchronized T dequeue() {
        log.info("notified");
        return this.values.poll();
    }
}
