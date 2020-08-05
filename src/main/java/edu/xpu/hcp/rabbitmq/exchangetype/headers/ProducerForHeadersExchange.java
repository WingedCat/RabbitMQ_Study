package edu.xpu.hcp.rabbitmq.exchangetype.headers;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class ProducerForHeadersExchange {
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
        String exchangeName = "headers_exchange";
        String routingKey = "waring.user";
        Map<String,Object> headers = new HashMap<>();
        headers.put("source","usermodule");
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .headers(headers)
                .build();

        /**
         * 方法参数解析：basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
         * exchange:发送消息的交换器名称，此处为""使用默认交换器
         * routingKey:路由键
         * props:消息的其他属性，如果routing header等
         * body:消息内容
         */
        channel.basicPublish(exchangeName,routingKey,properties,"waring message".getBytes());

        //5、关闭相关连接
        channel.close();
        connection.close();

    }
}
