package com.data.dataexchangeplatform.monitor;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Enumeration;


public class monitor {

    private String brokerURL = "tcp://localhost:61616";
    private String queueName = "data.exchange.queue";




//    监控和管理：平台可以提供监控和管理功能，包括查看消息状态、统计消息量、监控队列等。

    //查看消息状态
    public void getQueueStatus() throws JMSException {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(queueName);
        MessageConsumer consumer = session.createConsumer(queue);
        while (true) {
            Message message = consumer.receive(1000);
            if (message != null) {
                System.out.println("Received message: " + message);
            } else {
                break;
            }
        }
        consumer.close();
    }



    //统计消息量
    public void getQueueCount() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(queueName);
        QueueBrowser browser = session.createBrowser(queue);
        Enumeration<?> enumeration = browser.getEnumeration();
        int count = 0;
        while (enumeration.hasMoreElements()) {
            enumeration.nextElement();
            count++;
        }
        System.out.println("Queue count: " + count);
    }


    //监控队列
    public void monitorQueue() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(queueName);
        QueueBrowser browser = session.createBrowser(queue);
        Enumeration<?> enumeration = browser.getEnumeration();
        while(enumeration.hasMoreElements()) {
            Message message = (Message) enumeration.nextElement();
            System.out.println("Received message: " + message);
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                System.out.println("Received message: " + text);
            }
            if (message instanceof ObjectMessage) {
                ObjectMessage objectMessage = (ObjectMessage) message;
                Object object = objectMessage.getObject();
                System.out.println("Received message: " + object);
            }
            if (message instanceof MapMessage) {
                MapMessage mapMessage = (MapMessage) message;
                Enumeration<?> mapNames = mapMessage.getMapNames();
                while (mapNames.hasMoreElements()) {
                    String key = (String) mapNames.nextElement();
                    Object value = mapMessage.getObject(key);
                    System.out.println("Received message: " + key + "=" + value);
                }
            }
            if (message instanceof StreamMessage) {
                StreamMessage streamMessage = (StreamMessage) message;
                while (streamMessage.readBoolean()) {
                    System.out.println("Received message: " + streamMessage.readString());
                }
            }
            if (message instanceof BytesMessage) {
                BytesMessage bytesMessage = (BytesMessage) message;
                byte[] bytes = new byte[(int) bytesMessage.getBodyLength()];
                bytesMessage.readBytes(bytes);
                System.out.println("Received message: " + new String(bytes));
            }
        }
    }
    //重构成框架

    public static void main(String[] args) {
        monitor de = new monitor();
        try {


            //查看消息状态
            de.getQueueStatus();
            //统计消息量
            de.getQueueCount();
            //监控队列
            de.monitorQueue();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}