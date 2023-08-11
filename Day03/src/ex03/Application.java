

import java.util.HashMap;
import java.util.Map;

public class Application {
    private static Application instance;
    private final Map<String, String> optionValueMap = new HashMap<>();
    private String downloadFolder = "./downloads";
    private String urlsFile = "files_urls.txt";
    private int threadsCount = 1;

    public Application(String[] args) {
        boolean done = false;

        parseOptions(args);

        for (Map.Entry<String, String> entry : optionValueMap.entrySet()) {
            if (entry.getKey().equals("help") || entry.getKey().equals("h")) {
                printHelpMessage();
                done = true;
            } else if (entry.getKey().equals("version") || entry.getKey().equals("v")) {
                System.out.println("Version 1.0");
                done = true;
            }else if (entry.getKey().equals("downloadFolder")) {
                downloadFolder = optionValueMap.get("downloadFolder");
            } else if (entry.getKey().equals("urlsFile")) {
                urlsFile = optionValueMap.get("urlsFile");
            } else if (entry.getKey().equals("threadsCount")) {
                try {
                    threadsCount = Integer.parseInt(optionValueMap.get("threadsCount"));
                } catch (NumberFormatException e) {
                    System.err.println("threadsCount must be a number");
                    System.exit(1);
                }
            } else {
                System.err.println("Unknown option: " + entry.getKey());
                System.exit(1);
            }
        }

        if (done) {
            System.exit(0);
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
        FileDownloader fileDownloader = new FileDownloader(downloadFolder, urlsFile, threadsCount);
        fileDownloader.start();
    }

    private void printHelpMessage() {
        System.out.println("Usage: java Program [options]");
        System.out.println("Options:");
        System.out.println("  --help, -h\t\t\tDisplay this help message.");
        System.out.println("  --downloadFolder=FOLDER, -h\t\t\tSet download folder to FOLDER");
        System.out.println("  --urlsFile=PATH, -h\t\t\tSet path to file with urls");
        System.out.println("  --threadsCount=NUMBER, -h\t\t\tSet number of threads to download files");
    }
}

