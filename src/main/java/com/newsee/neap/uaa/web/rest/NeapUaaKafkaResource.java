package com.newsee.neap.uaa.web.rest;

import com.newsee.neap.uaa.service.NeapUaaKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/neap-uaa-kafka")
public class NeapUaaKafkaResource {

    private final Logger log = LoggerFactory.getLogger(NeapUaaKafkaResource.class);

    private NeapUaaKafkaProducer kafkaProducer;

    public NeapUaaKafkaResource(NeapUaaKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.send(message);
    }
}
