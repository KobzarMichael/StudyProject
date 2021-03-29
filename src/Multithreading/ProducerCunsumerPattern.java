package Multithreading;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
/*паттерн продюссер-консумер
!!!!!ВСЕ КЛАССЫ В ПАКЕТЕ java.util.concurrent ПОТОКОБЕЗОПАСНЫ!!!!!
BlockingQueue - интерфейс
ArrayBlockingQueue класс (реализация интерфейса)
 */
public class ProducerCunsumerPattern {

    //собственно с пом. класса BlockingQueue создаем стек с заданным размером 10
    private static BlockingQueue <Integer> queue = new ArrayBlockingQueue<>(10);
    static Random random = new Random();

//делаем метод продьюс, который будет симулировать к примеру входящие запросы от пользователей на сервер
    public static void produce () throws InterruptedException {
        while (true) queue.put(random.nextInt(100));
    }
//метод по обработке запроса, который снимает задачу с стека и обрабатывает е>
    public static void consume () throws InterruptedException {

        while (true) {
            Thread.sleep(100);
                System.out.println(queue.take());
                System.out.println("size " + queue.size());

        }
    }




    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consume();
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
