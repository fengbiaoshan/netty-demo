package com.miexample.netty.custom;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * MessageDecoder
 *
 * @author reck
 * @date 2018/06/19
 */
public class MessageDecoder extends ReplayingDecoder<MessageDecoder.MessageState> {
    public enum MessageState {
        LENGTH,
        CONTENT
    }

    public MessageDecoder() {
        super(MessageState.LENGTH);
    }

    private Message m = new Message();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()) {
            case LENGTH:
                int leng = in.readInt();
                if (leng > 0) {
                    m.setLength(leng);
                    checkpoint(MessageState.CONTENT);
                } else {
                    out.add(m);
                }
                break;
            case CONTENT:
                byte[] buf = new byte[m.getLength()];
                in.readBytes(buf);
                m.setContent(new String(buf, "utf-8"));
                out.add(m);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
