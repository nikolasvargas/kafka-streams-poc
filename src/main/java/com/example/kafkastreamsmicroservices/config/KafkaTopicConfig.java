package com.example.kafkastreamsmicroservices.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig extends KafkaConfig {

    @Bean
    public NewTopic templateTopic() {
        return TopicBuilder.name(TEMPLATE_TOPIC).build();
    }
}
