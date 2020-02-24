package com.UI;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.internal.chartpart.Chart;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MySwingWrapper<T extends Chart> extends SwingWrapper<T> {
    private List<T> charts = new ArrayList<>();
    private final List<XChartPanel<T>> chartPanels = new ArrayList();
    public MySwingWrapper(T chart) {
        super(chart);
        this.charts.add(chart);
    }

    @Override
    public JFrame displayChart() {
        final JFrame frame = new JFrame("XYChart");

        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    XChartPanel<T> chartPanel = new XChartPanel((Chart)MySwingWrapper.this.charts.get(0));
                    MySwingWrapper.this.chartPanels.add(chartPanel);
                    frame.add(chartPanel);
                    //frame.pack();
                    frame.setVisible(false);
                }
            });
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        } catch (InvocationTargetException var4) {
            var4.printStackTrace();
        }

        return frame;

    }

    @Override
    public void repaintChart(int index) {
        ((XChartPanel)this.chartPanels.get(index)).revalidate();
        ((XChartPanel)this.chartPanels.get(index)).repaint();
    }


}
