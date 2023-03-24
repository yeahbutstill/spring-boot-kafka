package com.yeahbutstill.springbootkafka.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import rx.Observable;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ProducerApp {

    private final String topicName;

    private Properties properties;

    private KafkaProducer<String, String> producer;

    public ProducerApp(String topicName) {
        this.topicName = topicName;
        setUpProperties();
        setUpProducer();
    }

    private void setUpProperties() {
        properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "0");
    }

    private void setUpProducer() {
        producer = new KafkaProducer<>(properties);
    }

    public Observable<RecordMetadata> send(String value) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, value, value);

        return Observable.from(producer.send(record));
    }

    public void run() {
        Observable.interval(1, TimeUnit.SECONDS)
                .map(Object::toString)
                .flatMap(this::send)
                .subscribe(result -> {
                    log.info("Success send message to partition {}", result.partition());
                });
    }

    public static void main(String[] args) throws IOException {
        new ProducerApp("ICS_ECORR_NDAPPROVAL").run();

        System.in.read();
    }

}
