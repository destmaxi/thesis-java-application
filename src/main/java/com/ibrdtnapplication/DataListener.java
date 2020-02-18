package main.java.com.ibrdtnapplication;

import org.ibrdtnapi.BundleHandler;
import org.ibrdtnapi.entities.Bundle;

import java.util.ArrayList;

public class DataListener extends Observer<FifoQueue<Bundle>> {
    private BundleHandler handler = null;
    private ArrayList<Bundle> pendingBundles = new ArrayList<>();

    DataListener(int port) {
        FifoQueue<Bundle> receivedBundles = new FifoQueue<>();
        new Dispatcher(receivedBundles, this, port);
    }

    void setHandler(BundleHandler handler) {
        this.handler = handler;
        for (Bundle bundle : pendingBundles)
            handler.onReceive(bundle);
    }

    @Override
    public void update(FifoQueue<Bundle> bundles) {
        if (handler != null) handler.onReceive(bundles.dequeue());
        else pendingBundles.add(bundles.dequeue());
    }
}
