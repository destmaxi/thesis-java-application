package com.UI;

import com.ibrdtnapplication.Configuration;
import org.apache.commons.cli.ParseException;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Application {
    public static void main(String[] args) throws InvocationTargetException, InterruptedException, IOException, ParseException {
        Configuration configuration = new Configuration(args);
        File file = new File(configuration.getOutputDir());
        if (!file.exists()) file.mkdirs();
        ArrayList<ChartRender> charts = new ArrayList<>();
        Map<String, JPanel> panels = new HashMap<>();
        MyCardLayout cardLayout = new MyCardLayout();

        for (File f: Objects.requireNonNull(file.listFiles())) {
            if (f.isDirectory()) {
                JPanel panel = new JPanel();
                for (File f1: Objects.requireNonNull(f.listFiles())) {
                    ChartRender chart = new ChartRender(f1.getName(),f1.getAbsolutePath());
                    charts.add(chart);
                    panel.add(chart.getFrame().getContentPane());
                    panels.put(f1.getParentFile().getName(), panel);
                }
                cardLayout.addCart(panel, f.getName());
            }
        }

        Path dir = Paths.get(configuration.getOutputDir());
        new WatchDir(dir, true, cardLayout, panels).start();

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
