package com.snapUrl.User.Service.interceptors;

import com.snapUrl.User.Service.producers.KafkaLogProducer;
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
        enter.setService("user-service");
        enter.setMessage("➡️ Incoming request: http://localhost:8082"+request.getRequestURI());
        enter.setTimestamp(System.currentTimeMillis());
        logProducer.send(enter);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        LogEvent exit = new LogEvent();
        exit.setService("user-service");
        exit.setMessage("⬅️ Outgoing response: http://localhost:8082"+request.getRequestURI());
        exit.setTimestamp(System.currentTimeMillis());
        logProducer.send(exit);
    }
}
