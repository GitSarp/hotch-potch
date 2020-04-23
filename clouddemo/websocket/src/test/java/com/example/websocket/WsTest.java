package com.example.websocket;

import java.net.URI;

/**
 * @author 刘亚林
 * @description 测试类
 * @create 2019/11/5 10:51
 **/
public class WsTest {
    //final static String url = "ws://127.0.0.1:6001/bullet2";
    //final static String url = "ws://testWS.com/sharedb?nodeid=test";
    final static String url = "ws://127.0.0.1:8081/sharedb?nodeid=test";

    //线程数
    static int threadNum = 1;

    public static void main(String[] args) {
        WsTest wsTest = new WsTest();
        //开始时间
        for (int i = 0; i < threadNum; i++) {
            ClientEndpointTest test = null;
            try {
                test = new ClientEndpointTest(new URI(url));
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            new Thread(wsTest.new WorkerThread(test)).start();
        }

    }

    class WorkerThread implements Runnable{
        ClientEndpointTest test;
        WorkerThread(ClientEndpointTest test){
            this.test = test;
        }

        @Override
        public void run() {
            if(test == null){
                return;
            }
            try {
                synchronized (test) {
                    while (!test.isSessionStarted()) {
                        test.wait();
                    }
                }

                //test.sendTextMessage("hello!");
                Thread.sleep(10*60*1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
