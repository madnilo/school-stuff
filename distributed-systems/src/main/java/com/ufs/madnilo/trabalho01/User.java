package com.ufs.madnilo.trabalho01;

import java.io.IOException;
import java.util.Scanner;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class User {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws Exception {
		Scanner scan = new Scanner(System.in);
		String message;

		// conexão ao servidor
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("reindeer.rmq.cloudamqp.com");
		factory.setUsername("usngqcwq");
		factory.setPassword("OzkcoxFQqcEo4zDuSKTK0aWA9P9exzW_");
		factory.setVirtualHost("usngqcwq");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// criação de fila no servidor
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		// parte em que o receptor fica esperando mensagens
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);

		// testando doidices
		while (true) {
			System.out.println(">");
			message = scan.nextLine();

			channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + message + "'");

			if (message.equals("quit")) {
				scan.close();
				System.out.println("Saindo...");
				break;
			}
		}

	}
}
