# spring-boot-kafka

## Run consumer
```shell
./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic-java
```

kalau keluar message seperti berikut, ini karena pertama kali di consume topicnya belum ada. tapi setelah itu auto create sama si kafka
```text
[2023-03-24 00:50:51,418] WARN [Consumer clientId=console-consumer, groupId=console-consumer-50636] Error while fetching metadata with correlation id 2 : {topic-java=LEADER_NOT_AVAILABLE} (org.apache.kafka.clients.NetworkClient)
```

