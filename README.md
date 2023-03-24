# spring-boot-kafka

## Download kafka
```http request
https://kafka.apache.org/downloads
```

## Run Zoo and Kafka
```shell
./bin/zookeeper-server-start.sh config/zookeeper.properties
```
```shell
./bin/kafka-server-start.sh config/server.properties
```

## Run consumer
```shell
./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic ICS_ECORR_NDAPPROVAL 
```

kalau keluar message seperti berikut, ini karena pertama kali di consume topicnya belum ada. tapi setelah itu auto
create sama si kafka

```text
[2023-03-24 00:50:51,418] WARN [Consumer clientId=console-consumer, groupId=console-consumer-50636] Error while fetching metadata with correlation id 2 : {topic-java=LEADER_NOT_AVAILABLE} (org.apache.kafka.clients.NetworkClient)
```

## Crate topic baru
```shell
./bin/kafka-topics.sh --create --topic ICS-ECORR-NDAPPROVAL --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3
```

## Test Partition Rebalance
```text
- Jalankan Consumer main 3x
- Jalankan Producer main untuk mengirim datanya ke kafka
- Dan matikan 1 Consumer, secara automatis partisinya tidak ada yang konsume nah ini istilahnya adalah Partition Rebalance, dimana diakan di kirim ke consumer yang lain. 
```