package com.UI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class Application {
    public static void main(String[] args) {
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec("hostname");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(Objects.requireNonNull(proc).getInputStream()));
        String hostname = null;
        try {
            hostname = stdInput.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ChartRender render = new ChartRender("test", "/var/log/javaapp/" + hostname + "/data.log");
        try {
            render.render();
        } catch (FileNotFoundException e) {
            System.err.println("File not found, make sure the file has been created");
        }
    }
}
