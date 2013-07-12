# Benchmark
## Producer (or Sender)

```java
public static void main(String[] args) {
	final Sender sender = new SQSSender();

	sender.init();

	long count = 100000;

	for (long i = 0; i < count; i++) {
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
```

## Consumer (or Receiver)

```java
public static void main(String[] args) {
	Receiver receiver = new SQSReceiver();

	receiver.init();

	while (true) {
		receiver.receive(true);
	}
}
```