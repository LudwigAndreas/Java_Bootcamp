package model.User;

import exception.TransactionNotFoundException;
import model.Transaction.Transaction;
import model.Transaction.TransactionsLinkedList;
import model.Transaction.TransactionsList;

import java.util.UUID;

public class User {
    private final int id;
    private String name;
    private int balance;
    TransactionsList tl = new TransactionsLinkedList();

    public User(String name, int balance) {
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
    }

    public User(String name) {
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        this.balance = 0;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getBalance() {
        return this.balance;
    }

    public TransactionsList getTransactionsList() {
        return this.tl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction != null)
            this.tl.addTransaction(transaction);
        else throw new IllegalArgumentException("Transaction is not valid");
    }

    public void addBalance(int amount) {
        if (this.balance + amount < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance += amount;
    }

    public void subtractBalance(int amount) {
        if (this.balance - amount < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance -= amount;
    }

    public String toString() {
        return this.name + " (" + this.balance + ")" + " [" + this.id + "]";
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof User)) {
            return false;
        }

        User user = (User)obj;

        return this.id == user.getId();
    }

    public int hashCode() {
        return this.id;
    }

    public Transaction removeTransaction(UUID transferId) throws TransactionNotFoundException {
        Transaction transaction = this.tl.getTransaction(transferId);
        this.tl.deleteTransaction(transferId);
        return transaction;
    }
}
