package com.example.mqbenchmark.receiver;

public interface Receiver {
	public void init();

	public void receive(boolean show);
}