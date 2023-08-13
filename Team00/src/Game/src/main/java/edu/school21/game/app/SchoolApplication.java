package edu.school21.game.app;

import com.beust.jcommander.JCommander;
import com.diogonunes.jcdp.color.ColoredPrinter;
import edu.school21.game.exception.IllegalParametersException;
import edu.school21.game.logic.controller.Game;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class SchoolApplication {
    private static SchoolApplication instance = null;
    private JCommander jCommander = null;
    private ColoredPrinter cp = null;

    public static SchoolApplication getInstance(String[] args) {
        if (instance == null) {
            instance = new SchoolApplication(args);
        }
        return instance;
    }

    private SchoolApplication(String[] args) {
        this.cp = new ColoredPrinter.Builder(1, false).build();
        if (instance == null) {
            instance = this;
        } else {
            cp.errorPrintln("Application already created");
            System.exit(1);
        }

        CommandLineArgs commandLineArgs = parseArguments(args);

        boolean done = argsHandler(commandLineArgs);
        if (done) {
            System.exit(0);
        } else {
            initInteractive(
                    commandLineArgs,
                    PropertiesReader.getProperties(commandLineArgs.profile.toLowerCase())
            );
        }
    }

    private boolean argsHandler(CommandLineArgs commandLineArgs) {
        if (commandLineArgs.help) {
            jCommander.usage();
            return true;
        } else if (commandLineArgs.version) {
            cp.println("version 1.0");
            return true;
        } else if (commandLineArgs.size * commandLineArgs.size < commandLineArgs.enemiesCount + commandLineArgs.wallsCount + 2) {
            throw new IllegalParametersException("Too many enemies and walls for this map size");
        } else if (commandLineArgs.size < 2) {
            throw new IllegalParametersException("Map size must be at least 2");
        } else if (commandLineArgs.enemiesCount < 0) {
            throw new IllegalParametersException("Enemies count must be positive");
        } else if (commandLineArgs.wallsCount < 0) {
            throw new IllegalParametersException("Walls count must be positive");
        } else {
            return false;
        }
    }

    private CommandLineArgs parseArguments(String[] args) {
        CommandLineArgs commandLineArgs = new CommandLineArgs();
        jCommander = JCommander.newBuilder()
                .addObject(commandLineArgs)
                .build();

        try {
            jCommander.parse(args);
        } catch (Exception e) {
            cp.errorPrintln("Unknown argument");
            jCommander.usage();
            System.exit(1);
        }
        return commandLineArgs;
    }

    private void initInteractive(CommandLineArgs commandLineArgs, Properties properties) {
        Game game = new Game(
                commandLineArgs.profile,
                properties,
                cp,
                commandLineArgs.size,
                commandLineArgs.enemiesCount,
                commandLineArgs.wallsCount
        );
        game.run();
    }
}
