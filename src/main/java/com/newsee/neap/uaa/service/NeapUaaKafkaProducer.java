package com.newsee.neap.uaa.service;

import com.newsee.neap.uaa.config.KafkaProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NeapUaaKafkaProducer {

    private final Logger log = LoggerFactory.getLogger(NeapUaaKafkaProducer.class);

    private static final String TOPIC = "topic_neapuaa";

    private KafkaProperties kafkaProperties;

    private KafkaProducer<String, String> kafkaProducer;

    public NeapUaaKafkaProducer(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    public void init() {
        log.info("Kafka producer initializing...");
        kafkaProducer = new KafkaProducer<>(kafkaProperties.getProducerProps());
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        log.info("Kafka producer initialized");
    }

    public void send(String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, message);
        try {
            kafkaProducer.send(record);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void shutdown() {
        log.info("Shutdown Kafka producer");
        kafkaProducer.close();
    }
}
