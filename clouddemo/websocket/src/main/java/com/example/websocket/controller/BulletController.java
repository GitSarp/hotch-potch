package com.example.websocket.controller;

import com.example.websocket.vo.BulletMessageVO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author 刘亚林
 * @description
 * @create 2020/3/19 14:47
 **/
@Controller
public class BulletController {

    @MessageMapping("/chat")
    @SendTo("/toAll/bulletScreen")             //SendTo 发送至 Broker 下的指定订阅路径
    public String say(BulletMessageVO clientMessage) {
        String result=null;
        if (clientMessage!=null){
            result=clientMessage.getUsername()+":"+clientMessage.getMessage();
        }
        return result;
    }
}
