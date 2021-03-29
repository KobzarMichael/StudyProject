package Multithreading.Semaphor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
/*
семафор позволяет ограничить доступ к некому ресурсу, к примеру есть сервер (ресурс) и соединение (ресурс)
и мы хотим делить этот ресурс между потоками (семафор определяет сколько потоков смогут одновременно юзать ресурс Semaphore semaphore = new Semaphore(10); - 10 разрешений для потоков
 */
public class Connection {
    //реализуем паттерн синглтон (приватный конструктор и инициализация объекта внутри класса)
    private static Connection connection = new Connection();
    //собственно семафор с 10 пермитами
    private Semaphore semaphore = new Semaphore(10);
    //просто счетчик
    private int connectionsCount;

    private Connection() {

    }

    //обертка для doWork для того, чтобы в случае исключения в doWork метод семафор выполнился и освободил пермит
    //semaphor.release должен быть объявлен в finally
    public void work() throws InterruptedException {
        semaphore.acquire();
        try {
            doWork();
        } finally {
            semaphore.release();
        }

    }
//просто симуляция некой деятельности с коннекшенами
    public void doWork() throws InterruptedException {
        synchronized (this) {
            connectionsCount++;
            System.out.println(connectionsCount);
        }

        Thread.sleep(5000);

        synchronized (this) {
            connectionsCount--;
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) throws InterruptedException {
        //тут тебе сука уже должно быть все понятно
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        Connection connection = Connection.getConnection();

        for (int i = 0; i < 200; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        connection.work();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            //не забудь, что ты 15 минут сидел не мог понять в чем дело из-за того, что не там вызвал шатдаун
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);
    }
}
