package Multithreading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//представим что это аккаунт с бабками
public class DeadLockExample {
    //    // пара примеров для понимания:
//
//    Lock lock1 = new ReentrantLock();
//    Lock lock2 = new ReentrantLock();
//    public void method1 () {
//        synchronized (lock1) {
//            synchronized (lock2) {
//                Thread.sleep(1000); //просто симуляция
//            }
//        }
//    }
//
//    ДЕДЛОК НАСТУПИТ ТОГДА, КОГДА ЛОКИ БЛОЧАТСЯ В РАЗНОЙ ПОСЛЕДОВАТЕЛЬНОСТИ, КАК В ЭТИХ ДВУХ МЕТОДАХ
//    ЕСЛИ ЛОКИ БЛОЧАТСЯ В ОДНОЙ ПОСЛЕДОВАТЕЛЬНОСТИ, ТО ВСЕ БУДЕТ ОК
//
//    public void method2 () {
//        synchronized (lock2) {
//            synchronized (lock1) {
//                Thread.sleep(1000); //просто симуляция
//            }
//        }
//    }
//  ИЗБЕЖАТЬ ЭТОГО МОЖНО ИСПОЛЬЗУЯ ПРИМЕР СЛЕДУЮЩЕЙ КОНСТРУКЦИИ/МЕТОДА:
    private void takeLocks(Lock lock1, Lock lock2) {
        //СОЗДАЮТСЯ ДВЕ БУЛЕВСКИХ ПЕРЕМЕННЫХ ФОЛЗ ПО УМОЛЧАНИЮ
        boolean lock1Taken = false;
        boolean lock2Taken = false;

        //ДАЛЕЕ В БЕСКОНЕЧНОМ ЦИКЛЕ ПЫТАЕМСЯ ОДНОВРЕМЕННО ЗАЛОЧИТЬ ОБА ЛОКА С ПОМОЩЬЮ trylock();
        //ЕСЛИ ЗАЛОЧИЛИСЬ ДВА ЛОКА - ВСЕ ОК И ЦИКЛ ПРЕРЫВАЕТСЯ С ПОМОЩЬЮ return;
        //НО ЕСЛИ ЗАЛОЧИЛСЯ ТОЛЬКО ОДИН ЛОК - ТО МЫ ЕГО РАЗБЛОКИРУЕМ И СОВЕРШИМ НОВЫЙ ВИТОК ЦИКЛА,ПОКА НЕ БУДУТ ЗАЛОЧЕНЫ ОБА ЛОКА
        while (true) {
            try {
                lock1Taken = lock1.tryLock();
                lock2Taken = lock2.tryLock();
            } finally {
                if (lock1Taken && lock2Taken) {
                    return;
                }

                if (lock1Taken) lock1.unlock();

                if (lock2Taken) lock2.unlock();
            }
        }
    }
}
