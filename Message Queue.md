# Intro
Message Queue顧名思義，就是把生產者 (producer) 所生產的message丟到queue裡面，然後排隊等消費者 (consumer) 一筆一筆從queue拿message出來處理。

## Comparison
這邊survey了幾套MQ，一一概述如下。

### SQS
由AWS所提供的Message Queue，全名為Simple Queue Service。主要有以下特點：

* 單一message最大可以到256KB
* 保證至少讀取一次、處理完後要用timeout或delete來移除message
* receive的時候不保證message的順序

### RabbitMQ
用ErLang實作[AMQP](http://www.amqp.org/)的Message Queue，主要有以下特點：

* 單一message大小最大可以到2<sup>64</sup>位元組
* 不保證讀取到，但可以利用acknowledgment做出保證讀取一次的功能
* 保證receive的時候可以按照message的順序

## Benchmark
有兩個評測的標準，分別為每秒可接收以及每秒可送出幾次。

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