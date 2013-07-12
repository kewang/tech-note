package com.example.mqbenchmark.sender;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQSender implements Sender {
	private final static String QUEUE_NAME = "test-rabbitmq";

	private Connection connection;
	private Channel channel;

	@Override
	public void init() {
		ConnectionFactory factory = new ConnectionFactory();

		factory.setHost("localhost");

		try {
			connection = factory.newConnection();
			channel = connection.createChannel();

			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void send(Object obj, boolean show) {
		try {
			channel.basicPublish("", QUEUE_NAME, null, obj.toString()
					.getBytes());

			if (show) {
				System.out.println(obj.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}