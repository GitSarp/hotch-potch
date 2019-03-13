package pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 6:27 PM 2019/3/13
 * @ Description：
 * @ Modified By：
 */
public class WaitTermination {

    public static void main(String[] args) throws Exception {
        WaitTermination.executorService();
    }

    private static void executorService() throws Exception{
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(10) ;
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,5,1, TimeUnit.MILLISECONDS,queue) ;
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("running");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("run over");
            }
        });
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("running2");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("run2 over");
            }
        });

        //关闭线程池
        poolExecutor.shutdown();
        //不在接受新的线程，等待已有线程执行完毕
        while (!poolExecutor.awaitTermination(1,TimeUnit.SECONDS)){
            System.out.println("线程还在执行。。。");
        }
        System.out.println("main over");
    }
}
