package Multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MySynchronized extends Thread {


    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();
    //созданы объекты, чьи мониторы будут лочиться потоками
    //тобиш мы разграничили мониторы и теперь addCount1 & addCount2 будут выполняться пареллельно, но они не будут мешать друг-другу
    Object lock1 = new Object();
    Object lock2 = new Object();

    public void addCount1() {
        Random random = new Random();
        //синхронизируем метод на lock1
        synchronized (lock1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list1.add(random.nextInt(100));
        }

    }

    public void addCount2() {
        Random random = new Random();
        synchronized (lock2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list2.add(random.nextInt(100));
        }

    }

    public void work() {
        for (int i = 0; i < 1000; i++) {
            addCount1();
            addCount2();
        }
    }

    public void main() throws InterruptedException {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                work();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                work();
            }
        });
        long start = System.currentTimeMillis();
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        long end = System.currentTimeMillis();
        System.out.println("time tooked " + (end - start));

        System.out.println(list1.size());
        System.out.println(list2.size());

    }


    public static void main(String[] args) throws InterruptedException {
        MySynchronized mySynchronized = new MySynchronized();
        mySynchronized.main();
    }
}
