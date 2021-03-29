package Multithreading;

import java.util.Random;

public class InterruptLesson {


    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                for (int i = 0; i < 1_000_000_000; i++) {

                    //можно прервать с пом. исключения - если был вызван интерраптед, знач эксепшн будет пойман и будет
                    //вызван брейк
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        System.out.println("Thread was interrupted");
                        break;
                    }


                    //можно и так (явно проверяем был ли выставлен флаг интерраптед и если да, то прерываем поток
//                    if (Thread.currentThread().isInterrupted()) {
//                        break;
//                        System.out.println("Thread was interrupted");
//                    }
                    Math.sin(random.nextInt());
                }
            }
        });

        System.out.println("Starting thread...");

        thread.start();

        Thread.sleep(2000);
        //по факту интеррапт ставит флаг на true
        thread.interrupt();

        thread.join();

        System.out.println("Thread was finished!");


    }
}
