package com.data.dataexchangeplatform.packet;

public class DataExchangeMessage {
    private String sender; // 发送者
    private String receiver; // 接收者
    private String payload; // 消息内容

    public DataExchangeMessage(String sender, String receiver, String payload) {
        this.sender = sender;
        this.receiver = receiver;
        this.payload = payload;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
