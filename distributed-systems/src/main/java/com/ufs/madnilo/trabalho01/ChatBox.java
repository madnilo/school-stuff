package com.ufs.madnilo.trabalho01;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ChatBox {

	private static final String CLOUDAMPQ_HOST = "reindeer.rmq.cloudamqp.com";
	private static final String CLOUDAMPQ_USR = "usngqcwq";
	private static final String CLOUDAMOQ_PWD = "OzkcoxFQqcEo4zDuSKTK0aWA9P9exzW_";

	public static void main(String[] argv) throws Exception {
		Scanner scan = new Scanner(System.in);
		String xmlMessage = "", userQName, line, queue = "", prompt = ">";
		boolean comando = false;

		Channel channel = createChannel();
		userQName = createUserQ(channel, scan);

		// reception
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(userQName, true, consumer);

		// chat loop
		while (true) {
			System.out.println(prompt);
			line = scan.nextLine();

			if (line.startsWith("@")) {
				queue = line.replace("@", "");
				prompt = queue + ">";
				comando = true;
			} else {
				comando = false;
				Message msg = new Message(userQName, line);
				xmlMessage = objectToXML(msg);
			}

			if (!comando) {
				channel.basicPublish("", queue, null, xmlMessage.getBytes("UTF-8"));
				System.out.println(" [x] Sent '" + xmlMessage + "'");
			}

			if (xmlMessage.equals("quit")) {
				scan.close();
				System.out.println("Saindo...");
				break;
			}
			comando = false;
		}

	}

	private static String objectToXML(Message msg) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Message.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter sw = new StringWriter();

		m.marshal(msg, sw);
		return sw.toString();
	}

	private static String createUserQ(Channel channel, Scanner scan) throws IOException {
		String user;
		System.out.println("User:");
		user = scan.nextLine();
		channel.queueDeclare(user, false, false, false, null);
		return user;
	}

	private static Channel createChannel() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(CLOUDAMPQ_HOST);
		factory.setUsername(CLOUDAMPQ_USR);
		factory.setPassword(CLOUDAMOQ_PWD);
		factory.setVirtualHost(CLOUDAMPQ_USR);
		Connection connection = factory.newConnection();
		return connection.createChannel();
	}

}
