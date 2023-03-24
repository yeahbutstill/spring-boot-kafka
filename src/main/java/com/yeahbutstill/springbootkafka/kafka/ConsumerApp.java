package com.yeahbutstill.springbootkafka.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

@Slf4j
public class ConsumerApp {

    private final String topicName;

    private final String consumerGroup;

    private Properties properties;

    private KafkaConsumer<String, String> consumer;

    public ConsumerApp(String topicName, String consumerGroup) {
        this.topicName = topicName;
        this.consumerGroup = consumerGroup;

        setUpProperties();
        setUpConsumer();
    }

    private void setUpProperties() {
        properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
    }

    private void setUpConsumer() {
        consumer = new KafkaConsumer<>(properties);
    }

    public void run() {
        consumer.subscribe(Collections.singleton(topicName));

        Long timeout = 1000L;
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(timeout);
            for (ConsumerRecord<String, String> record : records) {
                log.info("receive {}:{} from partition {}", record.key(), record.value(), record.partition());
            }
        }
    }

    public static void main(String[] args) {
        new ConsumerApp("ICS-ECORR-NDAPPROVAL", "ICS").run();
    }

}