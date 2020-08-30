package com.example.kafkastreamsmicroservices.consumer;

import com.example.kafkastreamsmicroservices.config.DemoReceiverConfig;
import com.example.kafkastreamsmicroservices.config.KafkaConfig;
import com.example.kafkastreamsmicroservices.config.TemplateReceiverConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class ReceiverDemo {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(
            topics = KafkaConfig.TEMPLATE_TOPIC,
            groupId = DemoReceiverConfig.GROUPID,
            containerFactory = "demoKafkaListenerContainerFactory"
    )
    public void process(ConsumerRecord record, Acknowledgment ack) {
        boolean uncommittedOffset = true;

        while(uncommittedOffset) {
            try {
                handle(record);
                uncommittedOffset = false;
            } catch (Exception e) {
                LOG.error("Exception caught. Not committing offset to Kafka. {}", e.getMessage());
                uncommittedOffset = true;
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

        String key = this.getClass().getSimpleName() + "_handler";
        String message = "received and sending another message";
        try {
            Thread.sleep(5000);
            kafkaTemplate.send(KafkaConfig.TEMPLATE_TOPIC, key, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
