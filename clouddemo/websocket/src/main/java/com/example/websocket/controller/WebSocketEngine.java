package com.example.websocket.controller;

import org.springframework.web.socket.*;

/**
 * @author 刘亚林
 * @description    websocket mock server
 * @create 2019/10/22 16:21
 **/
public class WebSocketEngine implements WebSocketHandler {

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception{
        System.out.println("connection established ");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if(message instanceof TextMessage){
            String text = ((TextMessage)message).getPayload();
            System.out.println(System.currentTimeMillis() + ",receive msg: " + text);
        }
        session.sendMessage(new TextMessage("i got it,666!"));
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        System.out.println("connection closed");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
