package com.miexample.netty.custom;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * MessageDecoder
 *
 * @author reck
 * @date 2018/06/19
 */
public class MessageDecoder2 extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        Integer length = in.readInt();
        out.add(length);
        byte[] buf = new byte[length];
        in.readBytes(buf);
        out.add(new String(buf, "utf-8"));
    }
}
