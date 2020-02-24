package com.UI;

import com.ibrdtnapplication.Configuration;
import org.apache.commons.cli.ParseException;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Application {
    public static void main(String[] args) throws InvocationTargetException, InterruptedException, IOException, ParseException {
        Configuration configuration = new Configuration(args);
        File file = new File(configuration.getOutputDir());
        ArrayList<ChartRender> charts = new ArrayList<>();
        MyCardLayout cardLayout = new MyCardLayout();

        file.list((file1, name) -> {
             if(new File(file1, name).isDirectory()) {
                 ChartRender chart = new ChartRender("Heartbeat",configuration.getOutputDir() + "/" + name + "/data.log");
                 charts.add(chart);
                 JPanel panel = new JPanel();
                 panel.add(chart.getFrame().getContentPane());
                 cardLayout.addCart(panel, name);
             }
             return true;
        });

        Path dir = Paths.get(configuration.getOutputDir());
        new WatchDir(dir, false, cardLayout).start();

        SwingUtilities.invokeAndWait(() -> {
            charts.forEach(chart -> {
                try {
                    chart.render();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
            cardLayout.show();
        });
    }
}
