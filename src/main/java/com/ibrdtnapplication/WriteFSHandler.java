package main.java.com.ibrdtnapplication;

import org.ibrdtnapi.BundleHandler;
import org.ibrdtnapi.entities.Bundle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class WriteFSHandler implements BundleHandler {
    private static final Logger log = Logger.getLogger(WriteFSHandler.class.getName());
    private String path;

    WriteFSHandler(String path) {
        this.path = path;
    }

    @Override
    public void onReceive(Bundle bundle) {
        log.info("new bundle received: " + bundle.toString());
        log.info("source: " + bundle.getSource());
        File file = new File(path + "/" + bundle.getSource().replace("dtn://", "") + ".log");
        file.getParentFile().mkdirs();
        try {
            FileOutputStream ostream = new FileOutputStream(file, true);
            StringBuilder data = new StringBuilder();
            int numberOfBlocks = bundle.getNumberOfBlocks();
            for (int i = 0; i < numberOfBlocks; i++)
                data.append(new String(bundle.getDecoded(i)));
            log.info("received data: " + data.toString());
            ostream.write(data.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
