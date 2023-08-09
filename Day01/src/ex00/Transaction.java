
import java.util.UUID;

public class Transaction {
    private UUID id;
    private User recipient;
    private User sender;
    private Category category;
    private int amount;

    public enum Category {
        OUTCOME,
        INCOME
    }

    public Transaction(UUID id, User sender, User recipient, Transaction.Category category, int amount) {
        this.id = id;
        this.recipient = recipient;
        this.sender = sender;
        this.category = category;
        this.amount = amount;
        checkCategory();
    }

    public Transaction(User sender, User recipient, Transaction.Category category, int amount) {
        this.id = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.category = category;
        this.amount = amount;
        checkCategory();
    }

    public Transaction(UUID id, User sender, User recipient, int amount) {
        this.id = id;
        this.recipient = recipient;
        this.sender = sender;
        if (amount < 0) {
            this.category = Transaction.Category.OUTCOME;
        } else {
            this.category = Transaction.Category.INCOME;
        }
        this.amount = amount;
        checkCategory();
    }

    public Transaction(User sender, User recipient, int amount) {
        this.id = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        if (amount < 0) {
            this.category = Transaction.Category.OUTCOME;
        } else {
            this.category = Transaction.Category.INCOME;
        }
        this.amount = amount;
        checkCategory();
    }

    private void checkCategory() {
        if (recipient == null) {
            System.out.println("EXCEPTION: Recipient cannot be null");
            System.exit(1);
        } else if (sender == null) {
            System.out.println("EXCEPTION: Sender cannot be null");
            System.exit(1);
        } else if (category == null) {
            System.out.println("EXCEPTION: Category cannot be null");
            System.exit(1);
        } else if (amount == 0) {
            System.out.println("EXCEPTION: Amount cannot be 0");
            System.exit(1);
        } else if (category == Category.OUTCOME && amount > 0) {
            System.out.println("EXCEPTION: Outcome cannot be positive");
            System.exit(1);
        } else if (category == Category.INCOME && amount < 0) {
            System.out.println("EXCEPTION: Income cannot be negative");
            System.exit(1);
        } else if (category == Category.OUTCOME && sender.getBalance() - amount < 0) {
            System.out.println("EXCEPTION: Sender balance cannot be negative");
            System.exit(1);
        } else if (category == Category.INCOME && recipient.getBalance() + amount < 0) {
            System.out.println("EXCEPTION: Recipient balance cannot be negative");
            System.exit(1);
        }
    }

    public UUID getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public Category getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }

    public String toString() {
        return this.sender.getName() + " -> " +
                this.recipient.getName() + ", " +
                this.amount + ", " +
                this.category.toString() +
                ", transaction " + this.id.toString();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Transaction)) {
            return false;
        } else {
            Transaction t = (Transaction) o;
            return this.id.equals(t.getId());
        }
    }

    public int hashCode() {
        return this.id.hashCode();
    }
}
