package edu.xpu.hcp.rabbitmq.exchangetype.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerForTopicExchange {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1、创建一个ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
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
        String exchangeName = "topic_exchange";
        String routingKey1 = "waring.user";
        String routingKey2 = "info.news";
        String routingKey3 = "info.me";
        String routingKey4 = "waring.work";

        /**
         * 方法参数解析：basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
         * exchange:发送消息的交换器名称，此处为""使用默认交换器
         * routingKey:路由键
         * props:消息的其他属性，如果routing header等
         * body:消息内容
         */
        channel.basicPublish(exchangeName,routingKey1,null,"waring from user module".getBytes());
        channel.basicPublish(exchangeName,routingKey2,null,"info from news module".getBytes());
        channel.basicPublish(exchangeName,routingKey3,null,"info from me module".getBytes());
        channel.basicPublish(exchangeName,routingKey4,null,"waring from work module".getBytes());

        //5、关闭相关连接
        channel.close();
        connection.close();

    }
}
