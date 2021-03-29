package Multithreading;

import java.util.Scanner;

public class waitAndNotify {

    public void produce () throws InterruptedException {
        synchronized (this) {
            System.out.println("produce started...");

            wait();//заставляет текущий поток освободить монитор и ждать вызова нотифай
            // wait(1000) - поток будет ждать секунду
            //вейт блочится на текущем (this) потоке, если нужно залочить его на другом объекте - нужно явно его указать
            //пример есть локер lock1 - будет lock1.wait()
            System.out.println("produce finished");
        }
    }

    public void consume () throws InterruptedException {
        Thread.sleep(2000);
        Scanner scanner = new Scanner(System.in);

        synchronized (this) {
            System.out.println("waiting for enter...");
            scanner.nextLine();
            //текущий метод после вызова нотифай завершается и вейт активируется (поток после вейт продолжает работу)
            notify();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        waitAndNotify wn = new waitAndNotify();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    wn.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    wn.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
}
