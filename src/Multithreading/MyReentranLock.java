package Multithreading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyReentranLock {

    private int counter;
    //ReentrantLock - являет собой класс, который работает как synchronized, т.е блочит монитор и не позволяет устраивать гонку
    private Lock lock = new ReentrantLock();

    public void increment1 () {
        //собственно методы класса - всё просто
        lock.lock();
        try {
            counter++;
        } finally {
            //unlock должен быть вызван в finally блоке, т.к если в counter будет ошибка, то метод не дойдет до анлока
            // и монитор всегда будет захвачен потоком
            lock.unlock();
        }

    }

    public void increment2 () {
        lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }
    }

    public void showCounter () {
        System.out.println(counter);
    }

    public static void main(String[] args) throws InterruptedException {
        MyReentranLock myReentranLock = new MyReentranLock();
        long start = System.currentTimeMillis();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    myReentranLock.increment1();
                }

            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    myReentranLock.increment1();
                }

            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        long end = System.currentTimeMillis();
        myReentranLock.showCounter();
        System.out.println("millis "+ (end-start));

    }
}
