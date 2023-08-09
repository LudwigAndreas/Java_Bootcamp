
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

    public Transaction(User recipient, User sender, Category category, int amount) {
        this.id = UUID.randomUUID();
        if (recipient == null) {
            System.out.println("EXCEPTION: Recipient cannot be null");
        }
        this.recipient = recipient;
        if (sender == null) {
            System.out.println("EXCEPTION: Sender cannot be null");
        }
        this.sender = sender;
        if (category == null) {
            System.out.println("EXCEPTION: Category cannot be null");
        } else
        if (checkCategory(category, amount, sender, recipient)){
            this.category = category;
            this.amount = amount;
        } else {
            this.category = category;
            this.amount = 0;
        }
    }

    public Transaction(User recipient, User sender, int amount) {
        this.id = UUID.randomUUID();
        if (recipient == null) {
            System.out.println("EXCEPTION: Recipient cannot be null");
        }
        this.recipient = recipient;
        if (sender == null) {
            System.out.println("EXCEPTION: Sender cannot be null");
        }
        this.sender = sender;
        if (amount < 0) {
            this.category = Category.OUTCOME;
        } else {
            this.category = Category.INCOME;
        }
        if (checkCategory(this.category, amount, sender, recipient)){
            this.amount = amount;
        } else {
            this.amount = 0;
        }
    }

    private boolean checkCategory(Category category, int amount, User sender, User recipient) {
        if (amount == 0) {
            System.out.println("EXCEPTION: Amount cannot be zero");
            return false;
        } else if (category == Category.OUTCOME && amount > 0) {
            System.out.println("EXCEPTION: Outcome cannot be positive");
            return false;
        } else if (category == Category.INCOME && amount < 0) {
            System.out.println("EXCEPTION: Income cannot be negative");
            return false;
        } else if (category == Category.OUTCOME && sender.getBalance() - amount < 0) {
            System.out.println("EXCEPTION: Sender balance cannot be negative");
            return false;
        } else if (category == Category.INCOME && recipient.getBalance() + amount < 0) {
            System.out.println("EXCEPTION: Recipient balance cannot be negative");
            return false;
        }
        return true;
    }

    public void transact() {
        if (this.category == Category.OUTCOME) {
            this.sender.subtractBalance(this.amount);
            this.recipient.addBalance(this.amount);
        } else if (this.category == Category.INCOME) {
            this.sender.addBalance(this.amount);
            this.recipient.subtractBalance(this.amount);
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
