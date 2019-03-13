package communicate;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 5:08 PM 2019/3/13
 * @ Description：CountDownLatch、CyclicBarrier、Semaphore
 * @ Modified By：
 */
public class CountDownLatchTest implements Runnable {
    static CountDownLatch countDownLatch = new CountDownLatch(2);

    CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

    //信号量，假设有5台机器
    Semaphore semaphore = new Semaphore(5);

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+" run over...");
        countDownLatch.countDown();
    }

    public static void main(String[] args) {
//        Thread thread1 = new Thread(new CountDownLatchTest());
//        thread1.setName("thread1");
//        Thread thread2 = new Thread(new CountDownLatchTest());
//        thread2.setName("thread2");
//        thread1.start();
//        thread2.start();
//
//        System.out.println("main is waiting...");
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("main over");

//        CountDownLatchTest test = new CountDownLatchTest();
//        Thread thread3 = new Thread(test.new CyclicBarrierTest(1000),"thread3");
//        Thread thread4 = new Thread(test.new CyclicBarrierTest(9000),"thread4");
//        thread3.start();
//        thread4.start();

        //10个工人，每人操作一台机器,只有操作完，其他工人才能使用
        CountDownLatchTest test = new CountDownLatchTest();
        for (int i = 0; i < 10; i++) {
            new Thread(test.new SemaphoreTest(i),"thread"+i).start();
        }
    }

    class CyclicBarrierTest implements Runnable{
        int processTime;

        public CyclicBarrierTest(int processTime) {
            this.processTime = processTime;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+" is processing...");
            try {
                Thread.sleep(processTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(Thread.currentThread().getName()+" is waiting...");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName()+" run over");
        }
    }

    class SemaphoreTest implements Runnable{
        int workerId;

        public SemaphoreTest(int workerId) {
            this.workerId = workerId;
        }

        @Override
        public void run() {
            //获取信号量
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("worker "+ workerId+" start working...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("worker "+ workerId+" work done...");
            //释放信号量
            semaphore.release();

        }
    }
}
