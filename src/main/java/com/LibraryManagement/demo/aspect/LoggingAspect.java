package com.LibraryManagement.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    @Pointcut("execution(* com.LibraryManagement.demo.service.*.*(..))")
    public void bookServiceMethods() {
    }

    @Around("bookServiceMethods")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        System.out.println("Method: " + methodName + " executed in:" + (end - start));

        return result;
    }
    @Before("bookServiceMethods")
    public void beforeServiceMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();
        System.out.println("Before Execution Method: " + methodName + " called with arguments: ");
        for (Object arg : args) {
            System.out.println("Argument: " + arg);
        }
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getMappingMethods() {
    }

    @Before("getMappingMethods()")
    public void logMethodArguments(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();
        System.out.println("GetMapping Method: " + methodName + " called with arguments: ");
        for (Object arg : args) {
            System.out.println("Argument: " + arg);
        }
    }

    @AfterThrowing(pointcut = "bookServiceMethods", throwing = "exception")
    public void exceptionLogging(JoinPoint joinPoint, Throwable exception) {
        System.err.println("Method: " + joinPoint.getSignature().getName() + ", Error: " + exception);
    }

    @Pointcut("@annotation(com.LibraryManagement.demo.annotation.Audit)")
    public void auditLogging(JoinPoint joinPoint) {
        System.out.println("Method with annotation Audit: " + joinPoint.getSignature().getName());
    }
}
