package com.miexample.netty.custom;

/**
 * Message
 *
 * @author reck
 * @date 2018/06/19
 */
public class Message {
    private Integer length;

    private String content;

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
        return "Message{" +
                "length=" + length +
                ", content='" + content + '\'' +
                '}';
    }
}
