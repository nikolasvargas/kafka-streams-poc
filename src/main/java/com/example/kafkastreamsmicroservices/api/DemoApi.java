package com.example.kafkastreamsmicroservices.api;

import com.example.kafkastreamsmicroservices.config.KafkaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class DemoApi {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping
    public String publishMessage(@RequestHeader String key, @RequestBody String message) {
        kafkaTemplate.send(KafkaConfig.TEMPLATE_TOPIC, key, message);
        return "Published Successfully!";
    }
}
