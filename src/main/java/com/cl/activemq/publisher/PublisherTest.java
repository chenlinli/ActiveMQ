package com.cl.activemq.publisher;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class PublisherTest {
    private static String url = "tcp://127.0.0.1:61616";

    private static String topicName="myTopic";
    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂,默认账号密码admin admin
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
        Connection connection = factory.createConnection();
        //启动MQ连接
        connection.start();
        //是否加事务，设置消息模式（默认自动签收），
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建主题：P2P模式（是队列方式）
        Topic myTopic = session.createTopic(topicName);
        //创建生产者
        MessageProducer producer = session.createProducer(myTopic);
        //创建消息
        for(int i=1;i<=10;i++){
            TextMessage textMessage = session.createTextMessage("消息i:" + i);
            //发消息
            producer.send(textMessage);
        }
        //关闭连接
        System.out.println("消息发送完毕！！");
    }
}
