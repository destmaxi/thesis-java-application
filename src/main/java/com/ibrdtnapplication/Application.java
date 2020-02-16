package main.java.com.ibrdtnapplication;

import org.apache.commons.cli.ParseException;
import org.ibrdtnapi.Api;
import org.ibrdtnapi.BpApplication;
import org.ibrdtnapi.entities.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {
    private static final Logger log = Logger.getLogger(Application.class.getName());
    private static BpApplication bpApplication = new BpApplication();

    public static void main(String[] args) throws IOException {
        Configuration config;
        try {
            config = new Configuration(args);
        } catch (ParseException e) {
            return;
        }

        log.info(config.getDebugLevel().getName());
        Logger root = Logger.getLogger("");
        root.setLevel(config.getDebugLevel());
        for (Handler handler : root.getHandlers()) {
            handler.setLevel(config.getDebugLevel());
        }
        log.log(Level.CONFIG, config.toString());
        log.info(config.getEid());

        Process proc = Runtime.getRuntime().exec("hostname");
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String hostname = stdInput.readLine();
        log.info(hostname);

        bpApplication.setHandler(new WriteFSHandler(config.getOutputDir()));
        bpApplication.setEid(config.getEid());

        int count = 0;
        log.info("NEIGHBOR LIST: " + bpApplication.getNeighborList().toString());
        List<String> neighbors = bpApplication.getNeighborList();
        for (String neighbor : neighbors) {
            Bundle bundle = new Bundle(neighbor + "/" + config.getDestination(), ("" + count + "\n").getBytes());
            bundle.setSource("dtn://" + hostname);

            if (config.isDestinationGroup())
                bundle.setFlags(Api.DESTINATION_IS_NOT_SINGLETON);

            log.info("Sending bundle to " + neighbor + "/" + config.getDestination());
            bpApplication.send(bundle);
            log.info("bundle " + count++ + " send");
        }
    }
}
