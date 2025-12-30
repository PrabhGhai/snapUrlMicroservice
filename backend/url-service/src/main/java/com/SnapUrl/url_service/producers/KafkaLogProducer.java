package com.SnapUrl.url_service.producers;

import com.snapurl.logging.LogEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaLogProducer {

    @Value("${kafka.traceId}")
    private  String traceId ;

    private static final String TOPIC = "central-logs";

    private final KafkaTemplate<String, LogEvent> kafkaTemplate;


    public void send(LogEvent logEvent) {
        kafkaTemplate.send(TOPIC, traceId, logEvent);
    }
}

