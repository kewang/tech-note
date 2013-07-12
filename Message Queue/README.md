# Intro
Message Queue顧名思義，就是把生產者 (producer) 所生產的message丟到queue裡面，然後排隊等消費者 (consumer) 一筆一筆從queue拿message出來處理。

## Comparison
這邊survey了幾套MQ，一一概述如下。

### SQS
由AWS所提供的Message Queue，全名為Simple Queue Service。主要有以下特點：

* 單一message最大可以到256KB
* 保證至少讀取一次、處理完後要用timeout或delete來移除message
* receive的時候不保證message的順序

#### 操作方式

##### Init
無論是Producer或是Consumer，都必須要先利用AWS的credential將SQSClient初始化，並且設定endpoint。

```java
try {
	sqs = new AmazonSQSClient(new PropertiesCredentials(getClass().getClassLoader().getResourceAsStream("AwsCredentials.properties")));

	sqs.setEndpoint("sqs.ap-northeast-1.amazonaws.com");
} catch (IOException e) {
	e.printStackTrace();
}
```

##### Producer

```java
req = new SendMessageRequest();

req.setQueueUrl(URL);
req.setMessageBody(obj.toString());

sqs.sendMessage(req);
```

##### Consumer

```java
req = new ReceiveMessageRequest(URL);
result = sqs.receiveMessage(req);

if (result.getMessages().isEmpty()) {
	throw new IllegalArgumentException("Message queue is already empty.");
} else {
	Message message = result.getMessages().get(0);
	String body = message.getBody();
	String handle = message.getReceiptHandle();
	DeleteMessageRequest delReq = new DeleteMessageRequest(URL, handle);

	sqs.deleteMessage(delReq);
}
```

### RabbitMQ
用ErLang實作[AMQP](http://www.amqp.org/)的Message Queue，主要有以下特點：

* 單一message大小最大可以到2<sup>64</sup>位元組
* 不保證讀取到，但可以利用acknowledgment做出保證讀取一次的功能
* 保證receive的時候可以按照message的順序

#### 安裝Server
以Ubuntu為例，官方的APT repository就已經有一個rabbitmq-server，但是版本比較舊。所以我們就另外新增一個RabbitMQ官方的repository，內容如下：

`deb http://www.rabbitmq.com/debian/ testing main`

安裝步驟如下：

```bash
wget http://www.rabbitmq.com/rabbitmq-signing-key-public.asc
sudo apt-key add rabbitmq-signing-key-public.asc
sudo apt-get update
sudo apt-get install rabbitmq-server #安裝rabbitmq server
sudo ulimit -n 1024 #限制系統最多一次可以開啟1024個檔案
```

可以使用service對rabbitmq-server做操作

`sudo service rabbitmq-server start/stop/restart/status`

##### Management開啟方式

鍵入以下指令就可以打開rabbitmq的management。連結為 http://localhost:15672/

`sudo rabbitmq-plugins enable rabbitmq_management`

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

## References
* [RabbitMQ Tutorial](http://www.rabbitmq.com/getstarted.html)：可以利用這幾個了解幾個常在MQ上使用的scenario。 **(非常推薦)**
* [與檔案系統及程序的限制關係： ulimit](http://linux.vbird.org/linux_basic/0320bash.php#variable_ulimit)