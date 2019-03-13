package com.example.nettydemo.server;

import com.example.nettydemo.coder.HeartbeatDecoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 2:04 PM 2019/3/13
 * @ Description：
 * @ Modified By：
 */
public class HeartbeatInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                //消息解码
                .addLast(new HeartbeatDecoder())
                //五秒没有收到消息 IdleStateHandler将读空闲事件 添加到 ChannelPipeline 中
                .addLast(new IdleStateHandler(5, 0, 0))
                //事件处理器
                .addLast(new HeartBeatSimpleHandle());
    }
}
