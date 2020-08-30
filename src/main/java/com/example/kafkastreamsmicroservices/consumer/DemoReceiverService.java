package com.example.kafkastreamsmicroservices.consumer;

import com.example.kafkastreamsmicroservices.config.DemoReceiverConfig;
import com.example.kafkastreamsmicroservices.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class DemoReceiverService extends ReceiverService{

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private static final String DEMO_CONTAINER_FACTORY = "demoKafkaListenerContainerFactory";

    @KafkaListener(
            topics = KafkaConfig.TEMPLATE_TOPIC,
            groupId = DemoReceiverConfig.GROUPID,
            containerFactory = DEMO_CONTAINER_FACTORY
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
}
