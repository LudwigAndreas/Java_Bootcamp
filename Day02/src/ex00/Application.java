
import controller.ConsoleMenu;
import model.FilePattern;
import service.FileRecognizer;
import service.SignionsParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Locale;

public class Application {
    public static void main(String[] args) {
        ConsoleMenu menu;
        try {
            List<FilePattern> filePatterns = SignionsParser.parse(new FileInputStream("signatures.txt"));
            FileRecognizer fileRecognizer = new FileRecognizer(filePatterns);
            if (args.length != 0 && args[0].toLowerCase(Locale.ROOT).equals("--profile=dev")) {
                menu = new ConsoleMenu(ConsoleMenu.Profile.DEV, fileRecognizer, System.out);
            } else {
                FileOutputStream output = new FileOutputStream("result.txt");
                menu = new ConsoleMenu(ConsoleMenu.Profile.PROD, fileRecognizer, output);
            }
            menu.run();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }
}
