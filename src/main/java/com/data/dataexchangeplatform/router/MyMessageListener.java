package com.data.dataexchangeplatform.router;

import javax.jms.Message;
import javax.jms.MessageListener;

public class MyMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        // 处理消息逻辑
        System.out.println("Received message: " + message);
    }

}
