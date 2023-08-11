
public class ProducerConsumer {
    private int amount;

    public ProducerConsumer(int amount) {
        this.amount = amount;
    }

    public void produce() throws InterruptedException {
        synchronized (this) {
            for (int i = 0; i < amount; ++i) {
                System.out.println("Egg");
                wait();
                notify();
            }
        }
    }

    public void consume() throws InterruptedException {
        synchronized (this) {
            for (int i = 0; i < amount; ++i) {
                System.out.println("Hen");
                notify();
                wait();
            }
        }
    }
}
