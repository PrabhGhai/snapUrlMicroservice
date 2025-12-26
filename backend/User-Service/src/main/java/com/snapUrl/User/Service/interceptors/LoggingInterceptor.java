package com.snapUrl.User.Service.interceptors;

import com.snapUrl.User.Service.producers.KafkaLogProducer;
import com.snapurl.logging.LogEvent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {

    //importing it from self created jar
    private final KafkaLogProducer logProducer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        UUID traceId = UUID.fromString(request.getHeader("X-Trace-Id"));
        LogEvent enter = new LogEvent();
        enter.setService("user-service");
        enter.setMessage("Trace Id : "+traceId+" | ➡️ Incoming request: http://localhost:8082"+request.getRequestURI());
        enter.setTimestamp(System.currentTimeMillis());
        logProducer.send(enter);
        MDC.put("X-Trace-Id", (traceId).toString()); // so that AOP can read
        //Interceptor -> MCD -> AOP
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UUID traceId = UUID.fromString(request.getHeader("X-Trace-Id"));
        LogEvent exit = new LogEvent();
        exit.setService("user-service");
        exit.setMessage("Trace Id : "+traceId+" |⬅️ Outgoing response: http://localhost:8082"+request.getRequestURI());
        exit.setTimestamp(System.currentTimeMillis());
        MDC.clear();
        logProducer.send(exit);
    }
}
