package com.snapurl.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${logging.kafka.topic:central-logs}")
    private String topic;

    public LogProducer(KafkaTemplate<String, String> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;

    }

    public void send(LogEvent logEvent) {
        try {
            String json = objectMapper.writeValueAsString(logEvent);
            kafkaTemplate.send(topic, logEvent.getService(), json);
        } catch (Exception e) {
            // fallback: do nothing or log locally
            System.err.println("Failed to send log to Kafka");
        }
    }
}
