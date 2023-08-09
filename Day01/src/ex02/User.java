
import java.util.UUID;

public class User {
    private int id;
    private String name;
    private int balance;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
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
}
