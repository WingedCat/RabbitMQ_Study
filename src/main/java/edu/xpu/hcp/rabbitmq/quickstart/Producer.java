package edu.xpu.hcp.rabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
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
        //4、通过Channel发送数据
        String msg = "Hello";
        for (int i = 1; i <= 6; i++) {
            /**
             * 方法参数解析：basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
             * exchange:发送消息的交换器名称，此处为""使用默认交换器
             * routingKey:路由键
             * props:消息的其他属性，如果routing header等
             * body:消息内容
             */
            channel.basicPublish("","test001",null,(msg+i).getBytes());
        }
        //5、关闭相关连接
        channel.close();
        connection.close();

    }
}
