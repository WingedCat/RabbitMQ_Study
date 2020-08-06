package edu.xpu.hcp.rabbitmq.customizeconsumer;

import com.rabbitmq.client.*;

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

        String exchangeName = "test_consumer_exchange";
        String routingKey = "consumer.save";

        String msg = "Hello RabbitMQ,Consumer message";

        for (int i = 0; i < 5; i++) {
            channel.basicPublish(exchangeName,routingKey,true,null,msg.getBytes());
        }

        channel.close();
        connection.close();



    }
}
