package edu.school21.game.app;

import edu.school21.game.exception.IllegalParametersException;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

public class PropertiesReader {
    public static Properties getProperties(String profile) {
        Properties properties = new Properties();
        try {
            properties.load(PropertiesReader.class.getResourceAsStream("/application-" + profile.toLowerCase() + ".properties"));
        } catch (IOException | NullPointerException e) {
            throw new IllegalParametersException("No such profile \"" + profile + "\"");
        }
        return properties;
    }
}
