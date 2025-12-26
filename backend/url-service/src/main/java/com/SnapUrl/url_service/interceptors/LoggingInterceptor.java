package com.SnapUrl.url_service.interceptors;

import com.SnapUrl.url_service.producers.KafkaLogProducer;
import com.snapurl.logging.LogEvent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {

    //importing it from self created jar
    private final KafkaLogProducer logProducer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        LogEvent enter = new LogEvent();
        enter.setService("url-service");
        enter.setMessage("➡️ Incoming request: http://localhost:8082"+request.getRequestURI());
        enter.setTimestamp(System.currentTimeMillis());
        logProducer.send(enter);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        LogEvent exit = new LogEvent();
        exit.setService("url-service");
        exit.setMessage("⬅️ Outgoing response: http://localhost:8082"+request.getRequestURI());
        exit.setTimestamp(System.currentTimeMillis());
        logProducer.send(exit);
    }

}
