package edu.xpu.hcp.rabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer2 {
    public static void main(String[] args) throws Exception {
        //1、创建一个ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("123.56.45.209");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setConnectionTimeout(6000000);
        //2、通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();
        //3、通过connection创建一个Channel
        Channel channel = connection.createChannel();
        //4、声明队列
        String queueName = "test001";
        channel.queueDeclare(queueName,false,false,false,null);
        //5、创建消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //6、设置Channel
        channel.basicConsume(queueName,true,consumer);

        while (true){
            //7、获取消息
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消息是:"+msg);
        }
    }
}
