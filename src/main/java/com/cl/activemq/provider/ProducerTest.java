package com.cl.activemq.provider;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ProducerTest {
    private static String url = "tcp://127.0.0.1:61616";

    private static String queueName="myQueue";
    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂,默认账号密码admin admin
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
        Connection connection = factory.createConnection();
        //启动MQ连接
        connection.start();
        //是否加事务（true:发消息必须提交），设置消息模式（默认自动签收），
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        //创建目标（队列）：P2P模式（是队列方式）
        Queue queue = session.createQueue(queueName);
        //创建生产者
        MessageProducer producer = session.createProducer(queue);
        //设置非持久化
       // producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //创建消息
        for(int i=1;i<=10;i++){
            TextMessage textMessage = session.createTextMessage("消息i:" + i);
            //发消息
            producer.send(textMessage);
            session.commit();
        }
        //关闭连接
        System.out.println("消息发送完毕！！");
    }
}
