package com.example.kafkastreamsmicroservices.config;

import com.fasterxml.jackson.databind.JsonSerializable;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.Map;

@Configuration
public class TemplateReceiverConfig extends KafkaReceiverConfig {
    public static final String TEMPLATE_GROUP_ID = "TEMPLATE_GROUP_ID";

    @Bean
    public ConsumerFactory<String, String> templateConsumerFactory() {
        Map<String, Object> config = consumerProps();
        config.put(ConsumerConfig.GROUP_ID_CONFIG, TEMPLATE_GROUP_ID);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, String> templateKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(templateConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }
}
