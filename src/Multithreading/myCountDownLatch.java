package Multithreading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class myCountDownLatch implements Runnable {
    private CountDownLatch countDownLatch;
    private int id;

    public myCountDownLatch(CountDownLatch countDownLatch, int id) {
        this.countDownLatch = countDownLatch;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        countDownLatch.countDown();
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 5; i++) {
            executorService.submit(new myCountDownLatch(countDownLatch, i));
            System.out.println("Latch needed to open "+(5-i));
        }

        executorService.shutdown();

        countDownLatch.await();

        System.out.println("Latch opened!");
    }
}
