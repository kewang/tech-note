package com.example.mqbenchmark.receiver;

import java.io.IOException;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

public class SQSReceiver implements Receiver {
	private static final String URL = "https://sqs.ap-northeast-1.amazonaws.com/286725822237/test-queue";

	private AmazonSQS sqs;
	private ReceiveMessageRequest req;
	private ReceiveMessageResult result;

	@Override
	public void init() {
		try {
			sqs = new AmazonSQSClient(new PropertiesCredentials(getClass()
					.getClassLoader().getResourceAsStream(
							"AwsCredentials.properties")));

			sqs.setEndpoint("sqs.ap-northeast-1.amazonaws.com");
		} catch (IOException e) {
			e.printStackTrace();
		}

		req = new ReceiveMessageRequest(URL);
	}

	@Override
	public void receive(boolean show) {
		result = sqs.receiveMessage(req);

		if (result.getMessages().isEmpty()) {
			throw new IllegalArgumentException(
					"Message queue is already empty.");
		} else {
			Message message = result.getMessages().get(0);
			String body = message.getBody();
			String handle = message.getReceiptHandle();
			DeleteMessageRequest delReq = new DeleteMessageRequest(URL, handle);

			sqs.deleteMessage(delReq);

			if (show) {
				System.out.println(body);
			}
		}
	}
}