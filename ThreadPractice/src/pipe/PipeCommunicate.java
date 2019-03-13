package pipe;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 6:34 PM 2019/3/13
 * @ Description：线程基于管道通信
 * @ Modified By：
 */
public class PipeCommunicate {
    public static void main(String[] args) throws IOException {
        doSomething();
    }

    public static void doSomething() throws IOException {
        //字符管道 ，PipedInputStream字节管道
        PipedReader pipedReader = new PipedReader();

        PipedWriter pipedWriter = new PipedWriter();

        //管道连接
        pipedWriter.connect(pipedReader);

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("send thread start...");
                try {
                    for (int i = 0; i < 10; i++) {
                        pipedWriter.write(i);
                        Thread.sleep(200);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        pipedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("send thread end...");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("receive thread start...");
                int content;

                try {
                    while ((content = pipedReader.read())!=-1){
                        System.out.println("receive  message:"+content);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("receive thread end...");
            }
        }).start();
    }



}
