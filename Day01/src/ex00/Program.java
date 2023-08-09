
public class Program {
    public static void main(String[] args) {

        System.out.println("=====================================");
        User user1 = new User("John", 1000);
        User user2 = new User("Mike", 500);

        Transaction transactionOutcome = new Transaction(user1, user2, Transaction.Category.OUTCOME, -300);
        System.out.println(transactionOutcome);
        user1.subtractBalance(300);
        System.out.println(user1);
        System.out.println(user2);

        Transaction transactionIncome = new Transaction(user2, user1, Transaction.Category.INCOME, 300);
        System.out.println(transactionIncome);
        user1.addBalance(300);
        System.out.println(user1);
        System.out.println(user2);
        System.out.println("=====================================");
    }
}
