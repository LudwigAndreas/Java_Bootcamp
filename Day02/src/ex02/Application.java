
import controller.CommandLine;

import java.util.HashMap;
import java.util.Map;

public class Application {
    private static Application instance;
    private final Map<String, String> optionValueMap = new HashMap<>();
    private String currentFolder = ".";

    private Application(String[] args) {
        boolean done = false;

        parseOptions(args);

        int idx = 0;
        boolean interactive = true;
        boolean console = false;
        while (idx < args.length) {
            if (matchesOption(args[idx], "help", "h")) {
                printHelpMessage();
                done = true;
            } else if (matchesOption(args[idx], "interactive", "i")) {
                console = true;
            } else if (matchesOption(args[idx], "console", "c")) {
                interactive = true;
            } else if (optionValueMap.containsKey("current-folder")) {
                currentFolder = optionValueMap.get("current-folder");
            }
            else {
                System.err.println("Unknown option: " + args[idx]);
                System.exit(1);
            }
            idx++;
        }
        if (done) {
            System.exit(0);
        }

        if (console) {
            initConsoleMain();
            System.exit(0);
        } else if (interactive) {
            initInteractiveMain();
            System.exit(0);
        }
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
            if (arg.startsWith("--") || arg.startsWith("-")) {
                if (arg.contains("=")) {
                    String[] parts = arg.split("=");
                    String option = parts[0].substring(2);
                    String value = parts[1];
                    optionValueMap.put(option, value);
                }
            }
        }
    }

    private boolean matchesOption(String arg, String option, String alias) {
        if (alias != null) {
            return arg.equals("--" + option) || arg.equals("-" + alias);
        } else {
            return arg.equals("--" + option);
        }
    }

    private void initInteractiveMain() {
        CommandLine commandLine = new CommandLine(currentFolder);
    }

    private void initConsoleMain() {

    }

    private void printHelpMessage() {
        System.out.println("Usage: java ex03.Main [options]");
        System.out.println("Options:");
        System.out.println("  --help, -h\t\t\tDisplay this help message.");
        // System.out.println("  --interactive, -i\t\tRun the application in interactive mode.");
        // System.out.println("  --console, -c\t\t\tRun the application in console mode.");
        System.out.println("  --current-folder=FOLDER, -h\t\t\tSet working folder to FOLDER");
    }
}
