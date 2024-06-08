package com.data.dataexchangeplatform;

import javax.jms.*;
import javax.sql.DataSource;

import com.data.dataexchangeplatform.packet.DataExchangeMessage;
import com.frameworkset.commons.dbcp2.BasicDataSource;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.jdbc.JDBCPersistenceAdapter;
import org.apache.activemq.store.jdbc.adapter.MySqlJDBCAdapter;

import java.io.IOException;


public class DataExchange {

    private String brokerURL = "tcp://localhost:61616";
    private String queueName = "data.exchange.queue";

    public void sendMessage(String message) throws JMSException, IOException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

//使用Activatemq 配置持久化存储

        BrokerService brokerService = new BrokerService();
        BasicDataSource yourDataSource = new BasicDataSource();
        yourDataSource.setUrl("jdbc:mysql://localhost:3306/activemq?useUnicode=true&characterEncoding=utf8");
        yourDataSource.setUsername("root");
        yourDataSource.setPassword("root");
        yourDataSource.setDriverClassName("com.mysql.jdbc.Driver");


        JDBCPersistenceAdapter persistenceAdapter = new JDBCPersistenceAdapter();
        persistenceAdapter.setDataSource(yourDataSource);
        persistenceAdapter.setAdapter(new MySqlJDBCAdapter());
        brokerService.setPersistenceAdapter(persistenceAdapter);



//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Session session = connection.createSession(true, Session.SESSION_TRANSACTED);

        Destination destination = session.createQueue(queueName);
        MessageProducer producer = session.createProducer(destination);

        TextMessage msg = session.createTextMessage(message);

        // 设置消息的持久性标志
        msg.setJMSDeliveryMode(DeliveryMode.PERSISTENT);


        producer.send(msg);

        session.commit();

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
        } catch (JMSException | IOException e) {
            e.printStackTrace();
        }
    }
}