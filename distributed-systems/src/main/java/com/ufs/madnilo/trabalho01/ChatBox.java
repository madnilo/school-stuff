package com.ufs.madnilo.trabalho01;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ChatBox {
	
	//Dados de conexão
	private static final String CLOUDAMPQ_HOST = "reindeer.rmq.cloudamqp.com";
	private static final String CLOUDAMPQ_USR = "usngqcwq";
	private static final String CLOUDAMOQ_PWD = "OzkcoxFQqcEo4zDuSKTK0aWA9P9exzW_";


	public static void main(String[] argv) throws Exception {
	
		//Identifica se linha digitada é comando ou mensagem a ser enviada.
		boolean comando = false;
		//Identifica se msg vai para grupo ou pessoa (exchange ou fila)
		boolean grupo = false;

		Scanner scan = new Scanner(System.in);
		String userQName, line, groupName, xmlMessage = "", sendTo = "", prompt = ">";
		String userToAddIntoGroup, groupToAddUserInto;
		String userToRemoveFromGroup, groupToRemoveUserFrom;

		//Cria canal e fila de usuário
		Channel channel = createChannel();
		userQName = createUserQ(channel, scan);

		//Looping de recepção
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				Message msg = new Message();
				String xmlMessage = new String(body, "UTF-8");
				try {
					msg = XMLToObject(xmlMessage);
				} catch (JAXBException e) {
					System.out.println("Falhou na desserialização da msg recebida!");
					e.printStackTrace();
				}
				System.out.println("("+ msg.getDate() +" às "+ msg.getTime() +") " + msg.getSender() + " diz: " + msg.getContent());
			}
		};
		channel.basicConsume(userQName, true, consumer);

		//Looping de interação
		while (true) {
			System.out.println(prompt);
			line = scan.nextLine();

			//Tratamento do conteúdo da linha digitada
			if (line.startsWith("@")) {
				comando = true;
				if(line.contains("@@")){
					grupo = true;
					sendTo = line.replace("@@", "");
					prompt = sendTo + "(grupo)>";
				}else{
					grupo = false;
					sendTo = line.replace("@", "");
					prompt = sendTo + ">";
				}
			}else if(line.startsWith("!")){
				comando = true;
				if(line.contains("create")){
					groupName = line.replaceAll("!creategroup ", "");
					channel.exchangeDeclare(groupName, "fanout");
					channel.queueBind(userQName, groupName, "");
				}else if(line.contains("remove")){
					groupName = line.replaceAll("!removegroup ", "");
					channel.exchangeDelete(groupName);
				}else if(line.contains("+")){
					String[] tokens = line.split("\\s");
					userToAddIntoGroup = tokens[1];
					groupToAddUserInto = tokens[2];
					channel.queueBind(userToAddIntoGroup, groupToAddUserInto, "");
				}else if(line.contains("-")){
					String[] tokens = line.split("\\s");
					userToRemoveFromGroup = tokens[1];
					groupToRemoveUserFrom = tokens[2];
					channel.queueUnbind(userToRemoveFromGroup, groupToRemoveUserFrom, "");
				}
			} else {
				comando = false;
				Message msg2 = new Message();
				if(grupo) msg2 = new Message(sendTo+"/"+userQName, line);
				else msg2 = new Message(userQName, line);
				xmlMessage = objectToXML(msg2);
			}
			
			//Envio de mensagem (caso linha digitada não seja comando)
			if (!comando) {
				if(grupo) channel.basicPublish(sendTo, "", null, xmlMessage.getBytes("UTF-8"));
				else channel.basicPublish("", sendTo, null, xmlMessage.getBytes("UTF-8"));
			}

			if (xmlMessage.equals("quit")) {
				scan.close();
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
	
	private static Message XMLToObject(String xmlMessage) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Message.class);
		Unmarshaller un = context.createUnmarshaller();
		StringReader sr = new StringReader(xmlMessage);
		return (Message) un.unmarshal(sr);
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
