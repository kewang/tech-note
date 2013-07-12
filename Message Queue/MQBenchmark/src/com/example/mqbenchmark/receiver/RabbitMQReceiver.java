package com.example.mqbenchmark.receiver;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class RabbitMQReceiver implements Receiver {
	private final static String QUEUE_NAME = "test-rabbitmq";

	private Connection connection;
	private Channel channel;
	private QueueingConsumer consumer;
	private boolean autoAck;

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

		consumer = new QueueingConsumer(channel);
		autoAck = false;

		try {
			channel.basicConsume(QUEUE_NAME, autoAck, consumer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void receive(boolean show) {
		try {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());

			if (show) {
				System.out.println(message);
			}

			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ShutdownSignalException e) {
			e.printStackTrace();
		} catch (ConsumerCancelledException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}