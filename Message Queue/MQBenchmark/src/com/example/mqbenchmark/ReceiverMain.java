package com.example.mqbenchmark;

import com.example.mqbenchmark.receiver.Receiver;
import com.example.mqbenchmark.receiver.SQSReceiver;

public class ReceiverMain {
	public static void main(String[] args) {
		Receiver receiver = new SQSReceiver();
		// Receiver receiver = new RabbitMQReceiver();

		receiver.init();

		while (true) {
			receiver.receive(true);
		}
	}
}