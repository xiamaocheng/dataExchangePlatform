package com.data.dataexchangeplatform;

import javax.jms.*;

import com.data.dataexchangeplatform.packet.DataExchangeMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class DataExchange {

    private String brokerURL = "tcp://localhost:61616";
    private String queueName = "data.exchange.queue";

    public void sendMessage(String message) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
        MessageProducer producer = session.createProducer(destination);

        TextMessage msg = session.createTextMessage(message);
        producer.send(msg);

        session.close();
        connection.close();
    }

    public String receiveMessage() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(queueName);
        MessageConsumer consumer = session.createConsumer(destination);

        TextMessage message = (TextMessage) consumer.receive(1000); // Wait 1 second for a message.
        String receivedMessage = null;
        if (message != null) {
            receivedMessage = message.getText();
        }

        session.close();
        connection.close();

        return receivedMessage;
    }


    //重构成框架

    public static void main(String[] args) {
        DataExchange de = new DataExchange();
        try {
            // Send a message
            //发送 DataExchangeMessage
            DataExchangeMessage dataExchangeMessage = new DataExchangeMessage("sender", "receiver", "Hello, MQ!");

            System.out.println("Sending message: " + dataExchangeMessage.getPayload());
            de.sendMessage(dataExchangeMessage.getPayload());



            // Receive a message
            String message = de.receiveMessage();
            System.out.println("Received message: " + message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}