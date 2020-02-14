package main.java.com.simpleapplication;

import org.ibrdtnapi.BpApplication;
import org.ibrdtnapi.BundleHandler;
import org.ibrdtnapi.entities.Bundle;

public class Application extends BpApplication {
    private BundleHandler handler = new PrintingHandler();

    public Application() {
        setHandler(this.handler);
    }

    @Override
    public void bundleReceived(Bundle b) {
        System.out.println("New bundle received.");
        super.bundleReceived(b);
    }
}
