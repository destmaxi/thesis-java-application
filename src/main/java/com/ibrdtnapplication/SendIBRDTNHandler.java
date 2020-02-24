package com.ibrdtnapplication;

import org.ibrdtnapi.BpApplication;
import org.ibrdtnapi.BundleHandler;
import org.ibrdtnapi.entities.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class SendIBRDTNHandler implements BundleHandler {
    private static final Logger log = Logger.getLogger("SendIBRDTNHandler");
    private BpApplication bpApplication;
    private String destination;

    SendIBRDTNHandler(BpApplication bpApplication, String destination) {
        this.bpApplication = bpApplication;
        this.destination = destination;
    }

    @Override
    public void onReceive(Bundle bundle) {
        log.info("notified");
        log.info(bundle.toString());
        //bpApplication.send(bundle);
        int count = 0;
        log.info("NEIGHBOR LIST: " + bpApplication.getNeighborList().toString());
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec("hostname");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(Objects.requireNonNull(proc).getInputStream()));
        String hostname = null;
        try {
            hostname = stdInput.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> neighbors = bpApplication.getNeighborList();
        for (String neighbor : neighbors) {
            Bundle newbundle = new Bundle(neighbor + "/" + destination, (bundle.getDestination()).getBytes());
            newbundle.setSource("dtn://" + hostname);

            log.info("BUNDLE: " + newbundle.toString());
            bpApplication.send(newbundle);
            log.info("bundle " + count++ + " send");
        }
    }
}
