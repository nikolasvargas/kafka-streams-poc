package com.example.kafkastreamsmicroservices.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public abstract class ReceiverService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public abstract void process(ConsumerRecord record, Acknowledgment ack);

    void handle(ConsumerRecord consumerRecord) {
        LOG.info(String.format("{key: value} -> {%s: %s}", consumerRecord.key(), consumerRecord.value()));
        LOG.info("Headers    -> {}", consumerRecord.headers());
        LOG.info("Partition  -> {}", consumerRecord.partition());
        LOG.info("Offset  -> {}", consumerRecord.offset());
    }
}
