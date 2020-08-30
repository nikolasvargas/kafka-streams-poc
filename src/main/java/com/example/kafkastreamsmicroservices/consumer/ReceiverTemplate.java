package com.example.kafkastreamsmicroservices.consumer;

import com.example.kafkastreamsmicroservices.config.KafkaConfig;
import com.example.kafkastreamsmicroservices.config.TemplateReceiverConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class ReceiverTemplate {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(
            topics = KafkaConfig.TEMPLATE_TOPIC,
            groupId = TemplateReceiverConfig.TEMPLATE_GROUP_ID,
            containerFactory = "templateKafkaListenerContainerFactory"
    )
    public void process(ConsumerRecord record, Acknowledgment ack) {
        boolean uncommitOffset = true;

        while(uncommitOffset) {
            try {
                handle(record);
                uncommitOffset = false;
            } catch (Exception e) {
                LOG.error("Exception caught. Not committing offset to Kafka. {}", e.getMessage());
                uncommitOffset = true;
            }
        }

        LOG.info("No exceptions, committing offsets.");
        ack.acknowledge();
    }

    private void handle(ConsumerRecord consumerRecord) {
        LOG.info(String.format("{key: value} -> {%s: %s}", consumerRecord.key(), consumerRecord.value()));
        LOG.info("Headers    -> {}", consumerRecord.headers());
        LOG.info("Partition  -> {}", consumerRecord.partition());
        LOG.info("Offset  -> {}", consumerRecord.offset());

    }
}
