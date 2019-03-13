package com.example.nettydemo.coder;

import com.example.nettydemo.message.CustomProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @ Author     ：freaxjj.
 * @ Date       ：Created in 12:30 PM 2019/3/13
 * @ Description：消息编码器
 * @ Modified By：
 */
public class HeartbeatEncoder extends MessageToByteEncoder<CustomProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, CustomProtocol msg, ByteBuf out) throws Exception {

        //消息的前八个字节为 header，剩余的全是 content
        out.writeLong(msg.getId()) ;
        out.writeBytes(msg.getContent().getBytes()) ;

    }
}
