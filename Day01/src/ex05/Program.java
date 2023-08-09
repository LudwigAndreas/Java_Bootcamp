

import controller.Menu;
import service.TransactionService;

public class Program {
    public static void main(String[] args) {
        Menu menu;
        TransactionService transactionService = new TransactionService();
        if (args.length != 0 && args[0].equals("--profile=dev")) {
            menu = new Menu(Menu.Profile.DEV, transactionService);
        } else {
            menu = new Menu(Menu.Profile.PROD, transactionService);
        }
        menu.run();
    }
}
