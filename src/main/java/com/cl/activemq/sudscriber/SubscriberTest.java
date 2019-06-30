package com.cl.activemq.sudscriber;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class SubscriberTest {
    private static String url = "tcp://127.0.0.1:61616";

    private static String topicName="myTopic";
    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂,默认账号密码admin admin
        System.out.println("消费者002");
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
        Connection connection = factory.createConnection();
        //启动MQ连接
        connection.start();
        //是否加事务，设置消息模式（默认自动签收），
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建主题：P2P模式（是队列方式）
        Topic myTopic = session.createTopic(topicName);
        //创建订阅者
        MessageConsumer consumer = session.createConsumer(myTopic);
        //启动监听消息
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                try {
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("接收消息:" + textMessage.getText());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}
