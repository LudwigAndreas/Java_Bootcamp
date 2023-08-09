

import exception.IllegalTransactionException;
import model.User.User;
import service.TransactionService;

import java.util.Arrays;

public class Program {
    public static void main(String[] args) throws IllegalTransactionException {
        {
            System.out.println("==== Example 1 ====");

            User user1 = new User("John", 1000);
            User user2 = new User("Mike", 1000);

            TransactionService transactionService = new TransactionService();
            transactionService.addUser(user1);
            transactionService.addUser(user2);

            System.out.println(user1.getName() + " balance: " + transactionService.getUserBalance(user1));
            System.out.println(user2.getName() + " balance: " + transactionService.getUserBalance(user2));

            transactionService.transferMoney(user1, user2, 500);
            System.out.println(user1.getName() + ": " + Arrays.toString(user1.getTransactionsList().toArray()));
            System.out.println(user2.getName() + ": " + Arrays.toString(user2.getTransactionsList().toArray()));

            System.out.println(user1.getName() + " balance: " + transactionService.getUserBalance(user1));
            System.out.println(user2.getName() + " balance: " + transactionService.getUserBalance(user2));

        }

        System.out.println("==== Example 2 ====");
        User user1 = new User("John", 1000);
        User user2 = new User("Mike", 1000);
        User user3 = new User("Lion", 1000);

        TransactionService transactionService = new TransactionService();
        transactionService.addUser(user1);
        transactionService.addUser(user2);
        transactionService.addUser(user3);

        System.out.println(user1.getName() + " balance: " + transactionService.getUserBalance(user1));
        System.out.println(user2.getName() + " balance: " + transactionService.getUserBalance(user2));
        System.out.println(user3.getName() + " balance: " + transactionService.getUserBalance(user3));

        transactionService.transferMoney(user1, user2, 500);
        transactionService.transferMoney(user2, user3, 200);
        transactionService.transferMoney(user3, user1, 300);

        System.out.println(user1.getName() + ": " + Arrays.toString(transactionService.getUserTransactions(user1).toArray()));
        System.out.println(user2.getName() + ": " + Arrays.toString(transactionService.getUserTransactions(user2).toArray()));
        System.out.println(user3.getName() + ": " + Arrays.toString(transactionService.getUserTransactions(user3).toArray()));

        System.out.println(user1.getName() + " balance: " + transactionService.getUserBalance(user1));
        System.out.println(user2.getName() + " balance: " + transactionService.getUserBalance(user2));
        System.out.println(user3.getName() + " balance: " + transactionService.getUserBalance(user3));

        System.out.println("==== Valid transactions ====");
        System.out.println("Valid transactions: " + Arrays.toString(transactionService.getValidTransactions()));
    }
}
