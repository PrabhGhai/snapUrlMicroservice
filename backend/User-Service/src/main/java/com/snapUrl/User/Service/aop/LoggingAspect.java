package com.snapUrl.User.Service.aop;


import com.snapUrl.User.Service.producers.KafkaLogProducer;
import com.snapurl.logging.LogEvent;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {


    private final KafkaLogProducer logProducer;

    @Around(
            "execution(* com.snapUrl.User.Service.controllers..*(..)) || " +
                    "execution(* com.snapUrl.User.Service.services..*(..)) || " +
                    "execution(* com.snapUrl.User.Service.repositories..*(..))"
    )
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();

        String methodName = joinPoint.getSignature().getName();

        LogEvent enter = new LogEvent();
        enter.setService("user-service");
        enter.setMessage( "Trace Id :"+ MDC.get("X-Trace-Id")+" | ➡️ ENTERING  " + className + "." + methodName + "()");
        enter.setTimestamp(System.currentTimeMillis());
        logProducer.send(enter);

        try {
            Object result = joinPoint.proceed();

            LogEvent exit = new LogEvent();
            exit.setService("user-service");
            exit.setMessage("Trace Id :"+MDC.get("X-Trace-Id")+" | ⬅️ EXITING   " + className + "." + methodName + "()");
            exit.setTimestamp(System.currentTimeMillis());
            logProducer.send(exit);

            return result;

        } catch (Exception ex) {
            LogEvent error = new LogEvent();
            error.setService("user-service");
            error.setMessage("Trace Id :"+MDC.get("X-Trace-Id")+" | ❌ EXCEPTION in " + className + "." + methodName + "() | message = " + ex.getMessage());
            error.setTimestamp(System.currentTimeMillis());
            logProducer.send(error);
            throw ex;
        }
    }
}
