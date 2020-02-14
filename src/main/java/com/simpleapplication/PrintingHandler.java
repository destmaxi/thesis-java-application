package main.java.com.simpleapplication;

import org.ibrdtnapi.BundleHandler;
import org.ibrdtnapi.entities.Bundle;

public class PrintingHandler implements BundleHandler {
    private int receivedBundles = 0;

    @Override
    public void onReceive(Bundle bundle) {
        this.receivedBundles ++;
        System.out.println("Received bundle (" + this.receivedBundles + "):" + bundle.toString().replace("\\n", " "));
    }
}
