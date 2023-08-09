package model.Transaction;

import exception.TransactionNotFoundException;
import model.User.User;

import java.util.UUID;

public class Transaction {
    private final UUID id;
    private final User recipient;
    private final User sender;
    private final Category category;
    private final int amount;

    public enum Category {
        OUTCOME,
        INCOME
    }

    public Transaction(UUID id, User sender, User recipient, Category category, int amount) {
        this.id = id;
        this.recipient = recipient;
        this.sender = sender;
        this.category = category;
        this.amount = amount;
        checkCategory();
    }

    public Transaction(User sender, User recipient, Category category, int amount) {
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
            this.category = Category.OUTCOME;
        } else {
            this.category = Category.INCOME;
        }
        this.amount = amount;
        checkCategory();
    }

    public Transaction(User sender, User recipient, int amount) {
        this.id = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        if (amount < 0) {
            this.category = Category.OUTCOME;
        } else {
            this.category = Category.INCOME;
        }
        this.amount = amount;
        checkCategory();
    }

    public boolean isValid() {
        try {
            sender.getTransactionsList().getTransaction(this.id);
            recipient.getTransactionsList().getTransaction(this.id);
        } catch (TransactionNotFoundException e) {
            return false;
        }
        return true;
    }

    private void checkCategory() {
        if (recipient == null) {
            System.out.println("EXCEPTION: Recipient cannot be null");
            throw new IllegalArgumentException("Recipient cannot be null");
        } else if (sender == null) {
            throw new IllegalArgumentException("Sender cannot be null");
        } else if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        } else if (amount == 0) {
            throw new IllegalArgumentException("Amount cannot be 0");
        } else if (category == Category.OUTCOME && amount > 0) {
            throw new IllegalArgumentException("Outcome cannot be positive");
        } else if (category == Category.INCOME && amount < 0) {
            throw new IllegalArgumentException("Income cannot be negative");
        } else if (category == Category.OUTCOME && sender.getBalance() - amount < 0) {
            throw new IllegalArgumentException("Sender balance cannot be negative");
        } else if (category == Category.INCOME && recipient.getBalance() + amount < 0) {
            throw new IllegalArgumentException("Recipient balance cannot be negative");
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

    public static Transaction income(User sender, User recipient, int amount) {
        return new Transaction(sender, recipient, Category.INCOME, amount);
    }

    public static Transaction outcome(User sender, User recipient, int amount) {
        return new Transaction(sender, recipient, Category.OUTCOME, amount);
    }

    public static Transaction inverse(Transaction t) {
        return new Transaction(t.getId(), t.getRecipient(), t.getSender(), t.getAmount() * -1);
    }

    public String toString() {
        return this.sender.getName() + " -> " +
                this.recipient.getName() + ", " +
                (this.amount > 0 ? "+" + this.amount :
                this.amount) + ", " +
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
