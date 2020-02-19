package com.ibrdtnapplication;

import org.apache.commons.cli.ParseException;
import org.ibrdtnapi.BpApplication;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {
    private static final Logger log = Logger.getLogger(Application.class.getName());
    private static BpApplication bpApplication = new BpApplication();
    private static DataListener dataListener;

    public static void main(String[] args) {
        Configuration config = null;
        try {
            config = new Configuration(args);
        } catch (ParseException e) {
            return;
        }

        setLogLevel(config.getDebugLevel());
        log.log(Level.CONFIG, config.toString());

        bpApplication.setHandler(new WriteFSHandler(config.getOutputDir()));
        bpApplication.setEid(config.getEid());
        dataListener = new DataListener(config.getPort());
        dataListener.setHandler(new SendIBRDTNHandler(bpApplication, config.getDestination()));
    }

    private static void setLogLevel(Level level) {
        Logger root = Logger.getLogger("");
        root.setLevel(level);
        for (Handler handler : root.getHandlers()) {
            handler.setLevel(level);
        }
    }
}
