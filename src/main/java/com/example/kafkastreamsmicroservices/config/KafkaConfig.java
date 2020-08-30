package com.example.kafkastreamsmicroservices.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig<T> {
    protected final String BOOTSTRAPSERVER = "localhost:9092";
    protected final int SERVERPORT = 8080;
    public static final String TEMPLATE_TOPIC = "TEMPLATE_TOPIC";
}
