

public class Program {
    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2 || !args[0].startsWith("--arraySize=")) {
            System.out.println("Usage: java Program --arraySize=NUMBER --threadCount=NUMBER");
            System.out.println("\t--arraySize - random array size.");
            System.out.println("\t--threadCount - number of threads that calculate array sum.");
            System.exit(-1);
        }

        int arraySize = Integer.parseInt(args[0].substring("--arraySize=".length()));
        int threadCount = Integer.parseInt(args[1].substring("--threadCount=".length()));
        if (arraySize < 0 || threadCount < 0 || threadCount > arraySize || arraySize > 2000000) {
            System.out.println("arraySize and threadCount must be positive and threadCount <= arraySize.");
            System.out.println("Usage: java Program --arraySize=NUMBER --threadCount=NUMBER");
            System.out.println("\t--arraySize - random array size. Must be positive and less than 2000000.");
            System.out.println("\t--threadCount - number of threads that calculate array sum. Must be positive and not greater than arraySize.");
            System.exit(-1);
        }

        int[] array = new int[arraySize];
        for (int i = 0; i < arraySize; ++i) {
            array[i] = (int) (Math.random() * 1000 + 1);
        }

        int checkSum = 0;
        for (int i = 0; i < arraySize; ++i) {
            checkSum += array[i];
        }
        System.out.println("Sum: " + checkSum);

        int step = (int) Math.ceil((double) arraySize / threadCount);

        SumCalculator[] threads = new SumCalculator[threadCount];
        for (int i = 0; i < threadCount; ++i) {
            threads[i] = new SumCalculator(i, array, i * step, Math.min((i + 1) * step - 1, arraySize - 1));
            threads[i].start();
        }

        int sum = 0;
        for (int i = 0; i < threadCount; ++i) {
            threads[i].join();
            sum += threads[i].getSum();
        }
        System.out.println("Sum by threads: " + sum);
    }
}
