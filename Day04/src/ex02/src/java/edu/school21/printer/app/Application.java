package edu.school21.printer.app;

import com.beust.jcommander.JCommander;
import edu.school21.printer.app.command.CommandLineArgs;
import edu.school21.printer.logic.ImageConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Application {
    private static Application instance = null;
    private String inputFilePath = "target/resources/image.bmp";

    private Application(String[] args) {
        boolean done = false;
        if (instance == null) {
            instance = this;
        } else {
            System.err.println("Application already created");
            System.exit(1);
        }

        CommandLineArgs commandLineArgs = new CommandLineArgs();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(commandLineArgs)
                .build();
        try {
            jCommander.parse(args);
        } catch (Exception e) {
            System.out.println("Unknown argument");
            jCommander.usage();
            System.exit(1);
        }
        if (commandLineArgs.help) {
            jCommander.usage();
            done = true;
        } else if (commandLineArgs.version) {
            System.out.println("version 1.0");
            done = true;
        }

        if (done) {
            System.exit(0);
        }

        if (commandLineArgs.inputFilePath != null) {
            inputFilePath = commandLineArgs.inputFilePath;
        }
        initConsoleMain(jCommander, commandLineArgs);
        System.exit(0);
    }

    public static Application getInstance(String[] args) {
        if (instance == null) {
            instance = new Application(args);
        }
        return instance;
    }

    private void initConsoleMain(JCommander jCommander, CommandLineArgs commandLineArgs) {
        try {
            BufferedImage image = ImageIO.read(new File(inputFilePath));
            if (commandLineArgs.black != null)
                ImageConverter.setBlack(commandLineArgs.black);
            if (commandLineArgs.white != null)
                ImageConverter.setWhite(commandLineArgs.white);
            ImageConverter.printAscii(image);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}


