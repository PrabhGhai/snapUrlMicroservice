package com.SnapUrl.api_gateway.filters;

import com.SnapUrl.api_gateway.producer.KafkaLogProducer;
import com.snapurl.logging.LogEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Order(-1)
public class LoggingGlobalFilter implements GlobalFilter{

    private final KafkaLogProducer logProducer;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String traceId = exchange.getRequest()
                .getHeaders()
                .getFirst("X-Trace-Id");

        if (traceId == null) {
            traceId = UUID.randomUUID().toString();
        }

        String finalTraceId = traceId;
        long startTime = System.currentTimeMillis();

        // attach traceId to request
        ServerHttpRequest mutatedRequest = exchange.getRequest()
                .mutate()
                .header("X-Trace-Id", finalTraceId)
                .build();

        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

        // 🔹 BEFORE REQUEST
        LogEvent enter = new LogEvent();
        enter.setService("api-gateway");
        enter.setMessage("Trace Id: " + finalTraceId +" | ➡️ Incoming request : " +  mutatedRequest.getMethod().name()+" - http://localhost:8080"+mutatedRequest.getURI().getPath() );
        enter.setTimestamp(System.currentTimeMillis());
        logProducer.send(enter);

        return chain.filter(mutatedExchange)
                .doOnSuccess(aVoid -> {
                    long timeTaken = System.currentTimeMillis() - startTime;

                    LogEvent exit =new LogEvent();
                    exit.setService("api-gateway");
                    exit.setMessage("Trace Id: "+finalTraceId+" | ⬅️ Response sent" );
                    exit.setStatus(mutatedExchange.getResponse().getStatusCode().value());
                    exit.setMethod(mutatedRequest.getMethod().name());
                    exit.setPath(mutatedRequest.getURI().getPath());
                    exit.setTimestamp(System.currentTimeMillis());
                    logProducer.send(exit);

                })
                .doOnError(ex -> {
                    LogEvent error = new LogEvent();
                    error.setService("api-gateway");
                    error.setMessage("Trace Id: "+finalTraceId+" | ❌ Error: " + ex.getMessage() );
                    error.setTimestamp(System.currentTimeMillis());
                    logProducer.send(error);
                });
    }
}

