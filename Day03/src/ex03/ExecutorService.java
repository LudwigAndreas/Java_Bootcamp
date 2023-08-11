

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ExecutorService {
    private final WorkerThread[] workers;
    private final BlockingQueue<Runnable> taskQueue;
    private int tasksInProgress;

    public ExecutorService(int numThreads) {
        this.workers = new WorkerThread[numThreads];
        this.taskQueue = new LinkedBlockingQueue<>();

        for (int i = 0; i < numThreads; i++) {
            workers[i] = new WorkerThread();
            workers[i].start();
        }
    }

    public void submit(Runnable task) {
        synchronized (this) {
            tasksInProgress++;
        }
        try {
            taskQueue.put(() -> {
                task.run();
                synchronized (this) {
                    tasksInProgress--;
                    if (tasksInProgress == 0) {
                        notifyAll();
                    }
                }
            });
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void waitForCompletion() throws InterruptedException {
        synchronized (this) {
            while (tasksInProgress > 0) {
                this.wait();
            }
        }
    }

    public void shutdown() {
        for (WorkerThread worker : workers) {
            worker.interrupt();
        }
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Runnable task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
