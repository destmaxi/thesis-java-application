package main.java.com.ibrdtnapplication;

import org.ibrdtnapi.entities.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class CommunicatorInput extends Observable<Bundle> implements Runnable {
    private static final Logger log = Logger.getLogger("CommunicatorInput");
    private ServerSocket serverSocket;

    CommunicatorInput(ServerSocket socket) {
        super("CommunicatorInput");
        serverSocket = socket;
    }

    @Override
    public void run() {
        String str;
        try {
            while (true) {
                BufferedReader br = new BufferedReader(new InputStreamReader(serverSocket.accept().getInputStream()));
                while ((str = br.readLine()) != null) {
                    log.info("new message");
                    update(new Bundle(str)); //(str)).get());
                    log.info("new message notified");
                }
                log.info("exit the while loop");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
