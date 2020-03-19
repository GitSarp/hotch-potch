package com.example.websocket;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.*;

/**
 * @author 刘亚林
 * @description
 * @create 2019/10/23 11:17
 **/

@ClientEndpoint
public class ClientEndpointTest {
    private final static ExecutorService pool = new ThreadPoolExecutor(600, 700,
                                      0L, TimeUnit.MILLISECONDS,
                                      new ArrayBlockingQueue<>(2500));

    //听见服务端会话
    private Session userSession = null;
    //会话结束标记
    public boolean sessionFinish = false;
    //会话开始标记
    public boolean sessionStarted = false;

    //连接正常断开时使用
    URI endpointURI = null;


    public ClientEndpointTest(URI endpointURI) throws Exception {
        this.endpointURI = endpointURI;
        asyncConnectToServer(this, endpointURI);
    }

    //开始连接时间
    volatile Long selfStartTime = 0L;

    private Future<Session> asyncConnectToServer(Object annotatedEndpointInstance, URI uri) throws Exception{
        return pool.submit(() -> {
            try {
                WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                //todo 测试代码
                container.setDefaultMaxSessionIdleTimeout(40000L);
                return(container.connectToServer(annotatedEndpointInstance, uri));
            } catch (DeploymentException | IOException | IllegalStateException  e) {
                throw e;
            }
        });
    }

    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    //最后一次消息发送时间
    volatile Long lastSendTime = 0L;

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println(System.currentTimeMillis() + ",connected!");
        synchronized(this){
            sessionStarted = true;
            this.notifyAll();
        }
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason      the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        this.userSession = null;
        synchronized (this) {
            sessionFinish = true;
            this.notifyAll();
        }
        System.out.println("disconnected!");
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println(System.currentTimeMillis() + ",receive server response:" + message);
    }

    @OnError
    public void onError(Throwable error) {
        System.out.println(error.getMessage());
    }

    public boolean isSessionFinish() {
        return sessionFinish;
    }

    public boolean isSessionStarted() {
        return sessionStarted;
    }

    public void sendTextMessage(String message) {
        if(userSession != null){
            synchronized (userSession){
                if(userSession != null && userSession.isOpen()){
                    this.userSession.getAsyncRemote().sendText(message);
                    lastSendTime = System.currentTimeMillis();
                }else {
                    System.out.println("parrot msg can't deliver because closed session: " + message);
                }
            }
        }else {
            System.out.println("parrot msg can't deliver because closed session: " + message);
        }
    }
}

