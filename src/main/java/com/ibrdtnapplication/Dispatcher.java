package main.java.com.ibrdtnapplication;

import org.ibrdtnapi.entities.Bundle;

import java.net.ServerSocket;

public class Dispatcher extends Observer<Bundle> {
    private FifoQueue<Bundle> receivedBundles;
    private ServerSocket socket = null;

    Dispatcher(FifoQueue<Bundle> receivedBundles, DataListener application, int port) {
        this.receivedBundles = receivedBundles;
        this.receivedBundles.addObserver(application);
        this.connect(port);
    }

    private void connect(int port) {
        try {
            this.socket = new ServerSocket(port);
            socket.setReuseAddress(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        CommunicatorInput communicatorInput = new CommunicatorInput(socket);
        communicatorInput.addObserver(this);
        Thread threadCommInput = new Thread(communicatorInput);
        threadCommInput.setName("CommunicatorInputThread");
        threadCommInput.start();
    }

    @Override
    public void update(Bundle value) {
        receivedBundles.enqueue(value);
    }
}
