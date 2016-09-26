package com.ufs.madnilo.exemplo;

import java.io.IOException;
import com.rabbitmq.client.*;

public class Receive {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("reindeer.rmq.cloudamqp.com");
		factory.setUsername("usngqcwq");
		factory.setPassword("OzkcoxFQqcEo4zDuSKTK0aWA9P9exzW_");
		factory.setVirtualHost("usngqcwq");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}
