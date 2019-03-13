package communicate;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 4:47 PM 2019/3/13
 * @ Description：
 * @ Modified By：
 */
public class VolatileTest implements Runnable{

    //volatile保证可见性。即线程对高速缓存中变量的修改，会立即刷新到主存中，其他线程访问的是最新值

    volatile boolean stopFlag = false;

    @Override
    public void run() {
        while (!stopFlag){
            System.out.println("子线程正在运行...");
        }
//        while (!Thread.currentThread().isInterrupted()){
//            System.out.println("子线程正在运行...");
//
//        }
        System.out.println("子线程结束");
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileTest volatileTest = new VolatileTest();
        Thread thread = new Thread(volatileTest);
        thread.start();

        Thread.sleep(100);
        volatileTest.stopThread();
//        thread.interrupt();
    }

    public void stopThread(){
        this.stopFlag = true;
    }

}
