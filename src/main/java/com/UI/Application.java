package com.UI;

import java.io.FileNotFoundException;

public class Application {
    public static void main(String[] args) {
        ChartRender render = new ChartRender("test", "/var/log/javaapp/maxime/data.log");
        try {
            render.render();
        } catch (FileNotFoundException e) {
            System.err.println("File not found, make sure the file has been created");
        }
    }
}
