package com.UI;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ChartRender {
    private static final String X_TITLE = "time";
    private static final String Y_TITLE = "dbm";
    private static final String SERIES_NAME = "Heartbeat";
    private int sizeToRender = 20;
    private MySwingWorker mySwingWorker;
    private MySwingWrapper<XYChart> sw;
    private XYChart chart;
    private JFrame frame;
    private String title;
    private String dataPath;

    public ChartRender(String title, String dataPath) {
        this.title = title;
        this.dataPath = dataPath;
        chart = QuickChart.getChart(this.title, X_TITLE, Y_TITLE, SERIES_NAME, new double[]{0}, new double[]{0});
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setXAxisTicksVisible(false);

        sw = new MySwingWrapper<>(chart);
        frame = sw.displayChart();
    }

    public void render() throws FileNotFoundException {
        mySwingWorker = new MySwingWorker();
        mySwingWorker.execute();
    }

    public void kill() {
        mySwingWorker.cancel(true);
        frame.removeAll();
        frame.dispose();
    }

    public JFrame getFrame() {
        return this.frame;
    }

    private class MySwingWorker extends SwingWorker<Boolean, double[]> {
        LinkedList<Double> fifo = new LinkedList<Double>();
        Scanner scanner;
        int numberOfLinesRead = 0;
        private File file;

        private MySwingWorker() throws FileNotFoundException {
            file = new File(dataPath);
            scanner = new Scanner(file);
        }

        @Override
        protected Boolean doInBackground() throws FileNotFoundException {

            while (!isCancelled()) {

                if (scanner.hasNextLine()) {
                    fifo.add(Double.parseDouble(scanner.nextLine()));
                    numberOfLinesRead++;
                } else {
                    scanner = new Scanner(file);
                    for (int i = 0; i < numberOfLinesRead; i++)
                        scanner.nextLine();
                }

                if (fifo.size() > sizeToRender) fifo.removeFirst();

                double[] renderArray = new double[fifo.size()];

                for (int i = 0; i < fifo.size(); i++) {
                    renderArray[i] = fifo.get(i);
                }
                publish(renderArray);

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    // eat it. caught when interrupt is called
                    System.out.println("MySwingWorker shut down.");
                }

            }

            return true;
        }

        @Override
        protected void process(List<double[]> chunks) {

            double[] mostRecentDataSet = chunks.get(chunks.size() - 1);

            chart.updateXYSeries(SERIES_NAME, null, mostRecentDataSet, null);
            sw.repaintChart();

            long start = System.currentTimeMillis();
            long duration = System.currentTimeMillis() - start;
            try {
                Thread.sleep(40 - duration); // 40 ms ==> 25fps
                // Thread.sleep(400 - duration); // 40 ms ==> 2.5fps
            } catch (InterruptedException e) {
            }

        }
    }
}
