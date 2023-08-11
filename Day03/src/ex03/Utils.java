

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    static public List<URL> readUrls(File urlsFile) {
        List<URL> urls = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(urlsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ", 2);
                if (parts.length == 2) {
                    try {
                        urls.add(new URL(parts[1]));
                    } catch (MalformedURLException e) {
                        System.err.println("Invalid url: " + parts[1]);
                        continue;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Can not read urls from file: " + urlsFile);
        }
        return urls;
    }
}
