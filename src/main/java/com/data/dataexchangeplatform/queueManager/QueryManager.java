package com.data.dataexchangeplatform.queueManager;

import com.data.dataexchangeplatform.packet.DataExchangeMessage;
import com.data.dataexchangeplatform.router.MyMessageListener;
import com.frameworkset.commons.dbcp2.BasicDataSource;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.jdbc.JDBCPersistenceAdapter;
import org.apache.activemq.store.jdbc.adapter.MySqlJDBCAdapter;

import javax.jms.*;
import java.io.IOException;
import java.util.Enumeration;


public class QueryManager {

    private String brokerURL = "tcp://localhost:61616";
    private String queueName = "data.exchange.queue";

    //添加队列
    public void addQueue() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(queueName);
        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        String message = null;
        producer.send(session.createTextMessage(message));
    }


    // 删除队列
    public void removeQueue() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        session.unsubscribe(queueName);
    }


    //查看队列的大小和状态
    public void getQueueStatus() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(queueName);
        QueueBrowser browser = session.createBrowser(queue);
        Enumeration<?> enumeration = browser.getEnumeration();
        while (enumeration.hasMoreElements()) {
            TextMessage textMessage = (TextMessage) enumeration.nextElement();
            System.out.println(textMessage.getText());
        }
    }



    //重构成框架

    public static void main(String[] args) {
        QueryManager de = new QueryManager();
        try {

            //添加队列
//            de.addQueue();
//            de.getQueueStatus();
            de.getQueueStatus();
            de.removeQueue();


        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}