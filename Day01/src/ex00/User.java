
import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private int balance;

    public User(String name, int balance) {
        this.id = UUID.randomUUID();
        this.name = name;
        if (balance < 0) {
            System.out.println("EXCEPTION: Balance cannot be negative");
            System.exit(-1);
        } else {
            this.balance = balance;
        }
    }

    public User(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.balance = 0;
    }

    public UUID getId() {
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

    public void addBalance(int amount) {
        if (this.balance + amount < 0) {
            System.out.println("EXCEPTION: Balance cannot be negative");
            System.exit(-1);
            return;
        }
        this.balance += amount;
    }

    public void subtractBalance(int amount) {
        if (this.balance - amount < 0) {
            System.out.println("EXCEPTION: Balance cannot be negative");
            System.exit(-1);
            return;
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

        return this.id.equals(user.getId());
    }

    public int hashCode() {
        return this.id.hashCode();
    }
}
