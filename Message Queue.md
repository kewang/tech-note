## Benchmark
### Producer (or Sender)
生產者可同時並發多條thread做為一次有大量的message (or payload)進到queue內。

```java
public static void main(String[] args) {
	final Sender sender = new SQSSender();	//可替換為不同的Sender，如RabbitMQSender

	sender.init();

	long count = 100000;

	for (long i = 0; i < count; i++) {
		final Payload p = new Payload();

		p.setId(i);
		p.setMsg(Integer.toString(new Random().nextInt()));

		new Thread(new Runnable() {
			@Override
			public void run() {
				sender.send(p, false);	//將message丟進queue內
			}
		}).start();
	}
}
```

### Consumer (or Receiver)
消費者會開啟一個infinite-loop，持續polling監控queue裡面是否有message需要處理。

```java
public static void main(String[] args) {
	Receiver receiver = new SQSReceiver();	//可替換為不同的Receiver，如RabbitMQReceiver

	receiver.init();

	while (true) {	//因為消費者永遠都會看queue裡面有沒有資料要處理，所以是個infinite-loop
		receiver.receive(true);	//將queue裡面的message取出來處理
	}
}
```