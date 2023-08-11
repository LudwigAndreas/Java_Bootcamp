package edu.school21.printer.app;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import edu.school21.printer.logic.ImageConverter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Application {
    private static Application instance = null;
    private final Map<String, String> optionValueMap = new HashMap<>();
    private String inputImage = null;
    private char black = 'O';
    private char white = '.';

    private Application(String[] args) {
        boolean done = false;
        if (instance == null) {
            instance = this;
        } else {
            System.err.println("Application already created");
            System.exit(1);
        }

        parseOptions(args);

        for (Map.Entry<String, String> entry : optionValueMap.entrySet()) {
            switch (entry.getKey()) {
                case "help":
                case "h":
                    printHelpMessage();
                    done = true;
                    break;
                case "version":
                case "v":
                    System.out.println("Version 1.0");
                    done = true;
                    break;
                case "inputImage":
                    inputImage = optionValueMap.get("inputImage");
                    break;
                case "blackChar":
                    black = optionValueMap.get("blackChar").charAt(0);
                    break;
                case "whiteChar":
                    white = optionValueMap.get("whiteChar").charAt(0);
                    break;
                default:
                    System.err.println("Unknown option: " + entry.getKey());
                    System.exit(1);
            }
        }

        if (done) {
            System.exit(0);
        }
        if (inputImage == null) {
            System.err.println("No input image");
            printHelpMessage();
            System.exit(1);
        }
        initConsoleMain();
        System.exit(0);
    }

    public static Application getInstance(String[] args) {
        if (instance == null) {
            instance = new Application(args);
        }
        return instance;
    }

    private void parseOptions(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            boolean twoDashes = arg.startsWith("--");
            if (twoDashes || arg.startsWith("-")) {
                String[] parts = arg.split("=");
                String option;
                if (twoDashes) {
                    option = parts[0].substring(2);
                } else {
                    option = parts[0].substring(1);
                }
                String value = null;
                if (arg.contains("=")) {
                    value = parts[1];
                }
                optionValueMap.put(option, value);
            }
        }
    }

    private void initConsoleMain() {
        try {
            BufferedImage image = ImageIO.read(new File(inputImage));
            ImageConverter.setBlack(black);
            ImageConverter.setWhite(white);
            String asciiArt = ImageConverter.convertToAscii(image);
            System.out.println(asciiArt);
        } catch (IOException e) {
            System.err.println("Error reading image");
        }
    }

    private void printHelpMessage() {
        System.out.println("Usage: java Program [options]");
        System.out.println("Options:");
        System.out.println("  --help, -h\t\t\tDisplay this help message.");
        System.out.println("  --version, -v\t\t\tDisplay this application version.");
        System.out.println("  --inputImage=PATH, -h\t\t\tPath to image to print");
        System.out.println("  --blackChar=CHAR, -h\t\t\tChar to print black pixels");
        System.out.println("  --whiteChar=CHAR, -h\t\t\tChar to print white pixels");
    }
}


