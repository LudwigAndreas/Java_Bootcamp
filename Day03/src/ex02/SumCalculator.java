
public class SumCalculator extends Thread {
    private final int[] array;
    private final int start;
    private final int end;
    private final int threadNumber;
    private int sum;

    public SumCalculator(int threadNumber, int[] array, int start, int end) {
        this.threadNumber = threadNumber;
        this.array = array;
        this.start = start;
        this.end = end;
        this.sum = 0;
    }

    @Override
    public void run() {
        for (int i = start; i <= end; ++i) {
            sum += array[i];
        }
        System.out.println("Thread " + threadNumber + " from: " + start + " to: " + end + " sum is: " + sum);
    }

    public int getSum() {
        return sum;
    }
}
