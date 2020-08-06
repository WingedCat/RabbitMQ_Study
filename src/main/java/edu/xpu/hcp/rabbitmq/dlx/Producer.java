package edu.xpu.hcp.rabbitmq.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setPassword("admin");
        connectionFactory.setUsername("admin");
        connectionFactory.setHost("IP");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.save";

        String msg = "Hello RabbitMQ,Dlx message";

        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().deliveryMode(2).contentEncoding("UTF-8").expiration("10000").build();
        for (int i = 0; i < 5; i++) {
            channel.basicPublish(exchangeName,routingKey,true,properties,msg.getBytes());
        }

        channel.close();
        connection.close();



    }
}
