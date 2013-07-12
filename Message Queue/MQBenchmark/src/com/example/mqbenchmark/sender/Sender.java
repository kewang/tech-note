package com.example.mqbenchmark.sender;

public interface Sender {
	public void init();

	public void send(Object obj, boolean show);
}