package com.example.mqbenchmark;

import java.util.Random;

import com.example.mqbenchmark.sender.SQSSender;
import com.example.mqbenchmark.sender.Sender;

public class SenderMain {
	private static final long COUNT = 100000;

	public static void main(String[] args) {
		final Sender sender = new SQSSender();
		// final Sender sender = new RabbitMQSender();

		sender.init();

		for (long i = 0; i < COUNT; i++) {
			final Payload p = new Payload();

			p.setId(i);
			p.setMsg(Integer.toString(new Random().nextInt()));

			new Thread(new Runnable() {
				@Override
				public void run() {
					sender.send(p, false);
				}
			}).start();
		}
	}
}