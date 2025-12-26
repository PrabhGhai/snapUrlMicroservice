package com.SnapUrl.url_service.aop;

import com.SnapUrl.url_service.producers.KafkaLogProducer;
import com.snapurl.logging.LogEvent;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {


    private final KafkaLogProducer logProducer;

    @Around(
            "execution(* com.SnapUrl.url_service.controllers..*(..)) || " +
                    "execution(* com.SnapUrl.url_service.services..*(..)) || " +
                    "execution(* com.SnapUrl.url_service.repositories..*(..))"
    )
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();

        String methodName = joinPoint.getSignature().getName();

        LogEvent enter = new LogEvent();
        enter.setService("url-service");
        enter.setMessage( "➡️ ENTERING  " + className + "." + methodName + "()");
        enter.setTimestamp(System.currentTimeMillis());
        logProducer.send(enter);

        try {
            Object result = joinPoint.proceed();

            LogEvent exit = new LogEvent();
            exit.setService("url-service");
            exit.setMessage("⬅️ EXITING   " + className + "." + methodName + "()");
            exit.setTimestamp(System.currentTimeMillis());
            logProducer.send(exit);

            return result;

        } catch (Exception ex) {
            LogEvent error = new LogEvent();
            error.setService("url-service");
            error.setMessage("❌ EXCEPTION in " + className + "." + methodName + "() | message = " + ex.getMessage());
            error.setTimestamp(System.currentTimeMillis());
            logProducer.send(error);
            throw ex;
        }
    }
}
