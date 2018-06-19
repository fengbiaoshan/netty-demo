package com.miexample.netty.keepalive;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * MessageDecoder
 *
 * @author reck
 * @date 2018/06/19
 */
public class MessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ReuseMessage m = new ReuseMessage();
        int type = in.readByte();
        m.setType(ReuseMessage.MessageType.values()[type]);
        int length = in.readInt();
        m.setLength(length);
        if (length > 0) {
            byte[] buf = new byte[length];
            in.readBytes(buf);
            String content = new String(buf, "utf-8");
            m.setContent(content);
        }
        out.add(m);

    }
}
