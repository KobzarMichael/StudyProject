package Multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyExecutorService implements Runnable {

    public static void main(String[] args) throws InterruptedException {

        //form a pool of threads (4 threads)
        ExecutorService workers = Executors.newFixedThreadPool(4);

        //give a tasks for thread pool
        for (int i = 0; i < 10; i++) {
            workers.submit(new MyExecutorService(i));
        }
//shutdowm means that tasks are given and our thread start working
        workers.shutdown();
        System.out.println("All task's accepted");

        //awaitTermination need to set i time that 'main' will wait for threads to complete
        workers.awaitTermination(1, TimeUnit.HOURS);

    }


    int id;

    public MyExecutorService(int id) {
        this.id = id;
    }
//just a rum method with some task
    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Task completed");
    }
}
