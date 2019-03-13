package communicate;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 3:58 PM 2019/3/13
 * @ Description：两线程交替打印数字
 * @ Modified By：
 */
public class WaitAndNotify {
    private static int num;
    private static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        WaitAndNotify waitAndNotify = new WaitAndNotify();

        Thread thread1 = new Thread(new OuThread());
        thread1.setName("OuThread");

        Thread thread2 = new Thread(new SiThread());
        thread2.setName("SiThread");

        thread1.start();
        thread2.start();

        //主线程wait 等待，等待子线程执行完 notify
        thread1.join();
        thread2.join();

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
}
