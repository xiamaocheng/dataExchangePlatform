//package com.data.dataexchangeplatform;
//
//import com.data.dataexchangeplatform.controller.queueController;
//import com.data.dataexchangeplatform.packet.DataExchangeMessage;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.jms.JMSException;
//import java.io.IOException;
//
//@SpringBootTest
//class DataExchangePlatformApplicationTests {
//    //�ع��ɿ��
//
//    public static void main(String[] args) {
//        queueController de = new queueController();
//        try {
//            // Send a message
//            //���� DataExchangeMessage
//            DataExchangeMessage dataExchangeMessage = new DataExchangeMessage("sender", "receiver", "Hello, MQ!");
//
//            System.out.println("Sending message: " + dataExchangeMessage.getPayload());
//            de.sendMessage(dataExchangeMessage.getPayload());
//
//
//
//            // Receive a message
//            String message = de.receiveMessage();
//            System.out.println("Received message: " + message);
//        } catch (JMSException | IOException e) {
//            e.printStackTrace();
//        }
//    }
//    @Test
//    void contextLoads() {
//    }
//
//}
