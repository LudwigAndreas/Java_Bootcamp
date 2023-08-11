
public class Program {
    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1 || !args[0].startsWith("--count=")) {
            System.out.println("Usage: java Program --count=NUMBER");
            System.out.println("\tNUMBER = number of words to be output by the threads.");
            System.exit(-1);
        }

        int amount = Integer.parseInt(args[0].substring("--count=".length()));
        if (amount < 0) {
            System.out.println("NUMBER can't be negative.");
            System.out.println("Usage: java Program --count=NUMBER");
            System.out.println("\tNUMBER = number of words to be output by the threads.");
            System.exit(-1);
        }

        ProducerConsumer producerConsumer = new ProducerConsumer(amount);
        Thread eggThread = new Thread(() -> {
            try {
                producerConsumer.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread henThread = new Thread(() -> {
            try {
                producerConsumer.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        eggThread.start();
        henThread.start();
        eggThread.join();
        henThread.join();
    }
}
