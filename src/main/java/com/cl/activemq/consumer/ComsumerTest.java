package com.cl.activemq.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ComsumerTest {
    private static String url = "tcp://127.0.0.1:61616";

    private static String queueName="myQueue";
    public static void main(String[] args) throws JMSException {
        System.out.println("消费者002");
        //1.创建连接工厂,默认账号密码admin admin
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
        Connection connection = factory.createConnection();
        //启动MQ连接
        connection.start();
        //是否加事务，设置消息模式（默认自动签收），
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建目标（队列）：P2P模式（是队列方式）
        Queue queue = session.createQueue(queueName);
        //创建消费者,消费队列的消息
        MessageConsumer consumer = session.createConsumer(queue);
        //启动监听消息
        consumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    try {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println("接收消息:" + textMessage.getText());
                        //textMessage.acknowledge();//手动签收成功确认
                       //session.commit();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
        });
        //不要关闭连接
    }
}
