package com.miexample.netty.keepalive;

/**
 * ReuseMessage
 *
 * @author reck
 * @date 2018/06/19
 */
public class ReuseMessage {
    public enum MessageType{
        HEARTBEAT,
        NORMAL
    }
    private MessageType type;

    private Integer length;

    private String content;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ReuseMessage{" +
                "type=" + type +
                ", length=" + length +
                ", content='" + content + '\'' +
                '}';
    }
}
