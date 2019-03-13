package com.example.nettydemo.client;


import com.example.nettydemo.coder.HeartbeatEncoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 12:34 PM 2019/3/13
 * @ Description：通道初始化器
 * @ Modified By：
 */
class CustomerHandleInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                //消息编码器
                .addLast(new HeartbeatEncoder())
                //10 秒没发送消息 IdleStateHandler 将写空闲事件添加到 ChannelPipeline 中
                .addLast(new IdleStateHandler(0, 10, 0))
                //通道绑定处理器，事件触发
                .addLast(new EchoClientHandle())
        ;
    }
}
