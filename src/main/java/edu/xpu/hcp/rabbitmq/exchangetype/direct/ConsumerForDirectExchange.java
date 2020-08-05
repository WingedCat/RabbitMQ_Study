package edu.xpu.hcp.rabbitmq.exchangetype.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class ConsumerForDirectExchange {
    public static void main(String[] args) throws Exception {
        //1、创建一个ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置属性
        connectionFactory.setHost("IP");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setConnectionTimeout(6000000);
        //2、通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();
        //3、通过connection创建一个Channel
        Channel channel = connection.createChannel();
        //4、声明
        String exchangeName = "direct_exchange";
        String exchangeType = "direct";
        String queueName = "test_queue";
        String routingKey = "waring";
        //声明交换器
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
        channel.queueDeclare(queueName,false,false,false,null);
        //将交换器和队列绑定
        channel.queueBind(queueName,exchangeName,routingKey);
        //5、创建消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //6、设置Channel
        channel.basicConsume(queueName,true,consumer);

        while (true){
            //7、获取消息
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();//等待下一个消息传递和返回。
            String msg = new String(delivery.getBody());
            System.out.println("消息是:"+msg);
        }
    }
}
