package main.java.com.ibrdtnapplication;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;

class Configuration {
    private String outputDir = "log";
    private String configFile = "config.properties";
    private String eid;
    private String dst;
    private Level debugLevel = Level.ALL;
    private int number = 10;
    private boolean eidGroup = false;
    private boolean dstGroup = false;

    Configuration(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine commandLine;

        Options options = new Options();
        Option help = new Option("h", "help", false, "print this message");
        Option configFile = new Option("c", "config-file", true, "set a configuration file");
        Option outputDir = new Option("o", "output-dir", true, "set the output directory to store the data, default is log/");
        Option eid = new Option("e", "eid", true, "set the eid to listen to");
        Option dst = new Option("dst", "destination", true, "set the destination eid where to send the data");
        Option eidGroup = new Option("eg", "eid-group", false, "set the eid as group");
        Option dstGroup = new Option("dg", "dst-group", false, "the destination is a group");
        Option number = new Option("n", "number", true, "number of messages to send, default 10");
        Option debug = new Option("d", "debug-level", true, "set the debug level");

        options.addOption(help);
        options.addOption(configFile);
        options.addOption(outputDir);
        options.addOption(eid);
        options.addOption(dst);
        options.addOption(eidGroup);
        options.addOption(dstGroup);
        options.addOption(number);
        options.addOption(debug);


        try {
            commandLine = parser.parse(options, args);

            if (commandLine.hasOption(help.getOpt())) {
                throw new ParseException("Help message"); // FIXME: quite ugly to handle that this way
            } else if (commandLine.hasOption(configFile.getOpt())) {
                this.configFile = commandLine.getOptionValue(configFile.getOpt());
                setConfig();
                return;
            } else {
                setConfig();
            }

            for (Option option : commandLine.getOptions()) {
                if (option.equals(configFile)) this.configFile = commandLine.getOptionValue(configFile.getOpt());
                else if (option.equals(outputDir)) this.outputDir = commandLine.getOptionValue(outputDir.getOpt());
                else if (option.equals(eid)) this.eid = commandLine.getOptionValue(eid.getOpt());
                else if (option.equals(dst)) this.dst = commandLine.getOptionValue(dst.getOpt());
                else if (option.equals(eidGroup)) this.eidGroup = true;
                else if (option.equals(dstGroup)) this.dstGroup = true;
                else if (option.equals(number))
                    this.number = Integer.parseInt(commandLine.getOptionValue(number.getOpt()));
                else if (option.equals(debug))
                    //this.debugLevel = Integer.parseInt(commandLine.getOptionValue(debug.getOpt()));
                    this.debugLevel = Level.parse(commandLine.getOptionValue(debug.getOpt()));
                else formatter.printHelp(" ", options);
            }

        } catch (ParseException e) {
            formatter.printHelp("[options] -e <arg> -dst <arg>", options);
            throw e;
        } catch (IOException e) {
            System.err.println("Invalid configuration file");
            throw new ParseException("Invalid configuration file");
        } catch (IllegalArgumentException e) {
            System.err.println("Enter a valid debug level");
            throw new ParseException("Invalid debug level");
        }
    }

    String getOutputDir() {
        return outputDir;
    }

    String getEid() {
        return isEidGroup() ? "dtn://" + eid : eid;
    }

    Level getDebugLevel() {
        return debugLevel;
    }

    String getDestination() {
        return dst;
    }

    int getNumber() {
        return number;
    }

    private boolean isEidGroup() {
        return eidGroup;
    }

    boolean isDestinationGroup() {
        return dstGroup;
    }

    private void setConfig() throws IOException, IllegalArgumentException {
        Properties properties = new Properties();
        InputStream inputStream = Objects.requireNonNull(this.getClass().getClassLoader().getResource(this.configFile)).openStream();
        properties.load(inputStream);

        if (!(properties.containsKey("eid") || properties.containsKey("dst")))
            throw new IOException(); // TODO: create constrain exception

        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            switch ((String) entry.getKey()) {
                case "eid":
                    this.eid = (String) entry.getValue();
                    break;
                case "dst":
                    this.dst = (String) entry.getValue();
                    break;
                case "isEidGroup":
                    this.eidGroup = Boolean.getBoolean((String) entry.getValue());
                    break;
                case "isDstGroup":
                    this.dstGroup = Boolean.getBoolean((String) entry.getValue());
                    break;
                case "outputDir":
                    this.outputDir = (String) entry.getValue();
                    break;
                case "number":
                    this.number = Integer.parseInt((String) entry.getValue());
                    break;
                case "debugLevel":
                    this.debugLevel = Level.parse((String) entry.getValue());
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return "\n\033[0;33mCONFIG: \n" +
                "\tconfigFile: " + configFile + "\n" +
                "\toutputDir: " + outputDir + "\n" +
                "\teid: " + eid + "\n" +
                "\tdst: " + dst + "\n" +
                "\tisEidGroup: " + eidGroup + "\n" +
                "\tisDstGroup: " + dstGroup + "\n" +
                "\tdebug level: " + debugLevel + "\n" +
                "\tnumber: " + number + "\n\033[0m";
    }
}
