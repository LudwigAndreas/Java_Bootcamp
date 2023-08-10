
import controller.ConsoleMenu;

import java.io.File;

public class Application {
    public static void main(String[] args) {
        ConsoleMenu menu;
        try {
            menu = new ConsoleMenu(new File("dictionary.txt"));
            menu.run(args);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
