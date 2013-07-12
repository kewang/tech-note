package com.example.mqbenchmark.sender;

import java.io.IOException;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class SQSSender implements Sender {
	private static final String URL = "https://sqs.ap-northeast-1.amazonaws.com/286725822237/test-queue";

	private AmazonSQS sqs;
	private SendMessageRequest req;

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

		req = new SendMessageRequest();
	}

	@Override
	public void send(Object obj, boolean show) {
		req.setQueueUrl(URL);
		req.setMessageBody(obj.toString());

		sqs.sendMessage(req);

		if (show) {
			System.out.println(obj.toString());
		}
	}
}