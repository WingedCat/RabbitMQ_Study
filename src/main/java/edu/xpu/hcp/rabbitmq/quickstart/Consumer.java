package edu.xpu.hcp.rabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;


public class Consumer {
    public static void main(String[] args) throws Exception {
        //1、创建一个ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置属性
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
        /**
         * 方法参数解析：queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete,Map<String, Object> arguments)
         * queue:队列名称
         * durable：队列持久化(服务器重启后数据依然存在)
         * exclusive:队列排他(限于此连接)
         * autoDelete:队列自动删除(队列不再使用时服务器会将队列自动删除)
         * arguments:队列的其他属性
         */
        channel.queueDeclare(queueName,false,false,false,null);
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
