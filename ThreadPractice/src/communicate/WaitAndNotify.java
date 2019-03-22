package communicate;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 3:58 PM 2019/3/13
 * @ Description：多线程顺序工作
 * @ Modified By：
 */
public class WaitAndNotify {
    private static int num;
    private static boolean flag = true;

    static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        WaitAndNotify waitAndNotify = new WaitAndNotify();

//        Thread thread1 = new Thread(new OuThread());
//        thread1.setName("OuThread");
//        Thread thread2 = new Thread(new SiThread());
//        thread2.setName("SiThread");
//        thread1.start();
//        thread2.start();

//        Thread threadA = new Thread(new AThread(),"AThread");
//        Thread threadB = new Thread(new BThread(),"BThread");
//        Thread threadC = new Thread(new CThread(),"CThread");
//        threadA.start();
//        threadB.start();
//        threadC.start();

        Thread thread4 = abcThread(i -> i%3==0,() -> System.out.println("A"));
        Thread thread5 = abcThread(i -> i%3==1,() -> System.out.println("B"));
        Thread thread6 = abcThread(i -> i%3==2,() -> System.out.println("C"));
        thread4.start();
        thread5.start();
        thread6.start();


        //主线程wait 等待，等待子线程执行完 notify
//        threadA.join();
//        threadB.join();
//        threadC.join();
        thread4.join();
        thread5.join();
        thread6.join();


        System.out.println("main over...");

    }

    static class OuThread implements Runnable{
//        WaitAndNotify waitAndNotify;
//
//        public OuThread(WaitAndNotify waitAndNotify) {
//            this.waitAndNotify = waitAndNotify;
//        }

        @Override
        public void run() {
            while (num<100){
                synchronized (WaitAndNotify.class){
                    if(!flag){
                        System.out.println(Thread.currentThread().getName()+" is runing:"+(++num));

                        flag = true;
                        WaitAndNotify.class.notify();
                    }else {
                        try {
                            WaitAndNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    static class SiThread implements Runnable{
//        WaitAndNotify waitAndNotify;
//
//        public SiThread(WaitAndNotify waitAndNotify) {
//            this.waitAndNotify = waitAndNotify;
//        }

        @Override
        public void run() {
            while (num<100){
                synchronized (WaitAndNotify.class){
                    if(flag){
                        System.out.println(Thread.currentThread().getName()+" is runing:"+(++num));

                        flag = false;
                        WaitAndNotify.class.notify();
                    }else {
                        try {
                            WaitAndNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    static class AThread implements Runnable {
        @Override
        public void run() {
            while (num < 30) {
                synchronized (WaitAndNotify.class){
                    if (num % 3 == 0) {
                        System.out.println(Thread.currentThread().getName());
                        ++num;
                        //唤醒所有wait
                        WaitAndNotify.class.notifyAll();
                    }else {
                        try {
                            WaitAndNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    static class BThread implements Runnable {
        @Override
        public void run() {
            while (num < 30) {
                synchronized (WaitAndNotify.class){
                    if (num % 3 == 1) {
                        System.out.println(Thread.currentThread().getName());
                        ++num;
                        //唤醒所有wait
                        WaitAndNotify.class.notifyAll();
                    }else {
                        try {
                            WaitAndNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    static class CThread implements Runnable {
        @Override
        public void run() {
            while (num < 30) {
                synchronized (WaitAndNotify.class){
                    if (num % 3 == 2) {
                        System.out.println(Thread.currentThread().getName());
                        ++num;
                        //唤醒所有wait
                        WaitAndNotify.class.notifyAll();
                    }else {
                        try {
                            WaitAndNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    static Thread abcThread(Predicate<Integer> conditin, Runnable runnable) {
        return new Thread(() -> {
            while (num < 30) {
                synchronized (WaitAndNotify.class){
                    if (conditin.test(num)) {
                        runnable.run();
                        num++;
                        WaitAndNotify.class.notifyAll();
                    }else {
                        try {
                            WaitAndNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    //如果是加锁的话,则有可能一直是某个线程获得锁吗
    static Thread abcThread2(Predicate<Integer> conditin, Runnable runnable) {
        return new Thread(() -> {
            while (num < 30) {
                lock.lock();
                //这里不清楚，为什么？
                if (num >= 30) {
                    lock.unlock();
                    break;
                }
                if (conditin.test(num)) {
                    runnable.run();
                    num++;
                }
                lock.unlock();
            }
        });
    }
}
