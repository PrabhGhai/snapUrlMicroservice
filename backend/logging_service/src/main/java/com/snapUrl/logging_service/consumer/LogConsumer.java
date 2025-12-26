package com.snapUrl.logging_service.consumer;

import com.snapurl.logging.LogEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class LogConsumer {
    @KafkaListener(
            topics = "central-logs", groupId = "logging-service-group")
    public void consume(LogEvent logEvent) {

        long timestampMillis = logEvent.getTimestamp();
        LocalDateTime localDateTime = Instant.ofEpochMilli(timestampMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("SERVICE   : " + logEvent.getService());
        System.out.println("MESSAGE   : " + logEvent.getMessage());
        System.out.println("TIME      : " + localDateTime);
    }
}
