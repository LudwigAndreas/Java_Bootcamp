
import exception.TransactionNotFoundException;

import java.util.Arrays;
import java.util.UUID;

public class Program {
    public static void main(String[] args) throws TransactionNotFoundException {
        User user1 = new User("John", 1000);
        User user2 = new User("Mike", 1500);
        User user3 = new User("Lion", 2000);

        TransactionsLinkedList transactionsLinkedList = new TransactionsLinkedList();

        Transaction t1 = new Transaction(user1, user2, 100);
        Transaction t2 = new Transaction(user2, user3, 150);
        Transaction t3 = new Transaction(user3, user1, 200);

        transactionsLinkedList.addTransaction(t1);
        transactionsLinkedList.addTransaction(t2);
        transactionsLinkedList.addTransaction(t3);

        user1.addTransaction(t1);
        user2.addTransaction(Transaction.inverse(t1));
        user2.addTransaction(t2);
        user3.addTransaction(Transaction.inverse(t2));
        user3.addTransaction(t3);
        user1.addTransaction(Transaction.inverse(t3));

        System.out.println("==== Transaction deleting ====");

        System.out.println("size: " + transactionsLinkedList.size());
        transactionsLinkedList.deleteTransaction(t2.getId());
        System.out.println("size: " + transactionsLinkedList.size());

        System.out.println(user1.getName() + ": " + Arrays.toString(user1.getTransactionsList().toArray()));
        System.out.println(user2.getName() + ": " + Arrays.toString(user2.getTransactionsList().toArray()));
        System.out.println(user3.getName() + ": " + Arrays.toString(user3.getTransactionsList().toArray()));


        System.out.println("==== Transaction deleting error ====");

        System.out.println("size: " + transactionsLinkedList.size());
       transactionsLinkedList.deleteTransaction(UUID.randomUUID());
        System.out.println("size: " + transactionsLinkedList.size());
    }
}
