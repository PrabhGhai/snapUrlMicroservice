package com.SnapUrl.url_service.interceptors;

import com.SnapUrl.url_service.producers.KafkaLogProducer;
import com.snapurl.logging.LogEvent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {

    //importing it from self created jar
    private final KafkaLogProducer logProducer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceId = request.getHeader("X-Trace-Id");
        LogEvent enter = new LogEvent();
        enter.setService("url-service");
        enter.setMessage("Trace Id :"+traceId+" | ➡️ Incoming request: http://localhost:8082"+request.getRequestURI());
        enter.setTimestamp(System.currentTimeMillis());
        logProducer.send(enter);
        MDC.put("X-Trace-Id", (traceId).toString());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String traceId = request.getHeader("X-Trace-Id");
        LogEvent exit = new LogEvent();
        exit.setService("url-service");
        exit.setMessage("Trace Id :"+traceId+" | ⬅️ Outgoing response: http://localhost:8082"+request.getRequestURI());
        exit.setTimestamp(System.currentTimeMillis());
        MDC.clear();
        logProducer.send(exit);
    }

}
