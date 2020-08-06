package edu.xpu.hcp.rabbitmq.limit;

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

        String exchangeName = "test_qos_exchange";
        String routingKey = "qos.limit";

        String msg = "Hello RabbitMQ,Limit message";

        for (int i = 0; i < 5; i++) {
            channel.basicPublish(exchangeName,routingKey,true,null,msg.getBytes());
        }

        channel.close();
        connection.close();



    }
}
