package org.prgrms.kdt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);
    // 패키지, 클래스, 메소드명, 인자   * org.prgrms.kdt..*Repository.*()
    // 혹은 @PointCut 이용, servicePublicMethodPointCut

//    @Pointcut("execution(public * org.prgrms.kdt..*.*(..))")
//    public void servicePublicMethodPointCut() {};
    //     @Around("org.prgrms.kdt.aop.CommonPointCut.repositoryInsertPointCut()")

    @Around("@annotation(org.prgrms.kdt.aop.TrackTime)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Before method called {}", joinPoint.getSignature().toString());
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        var endTime = System.nanoTime() - startTime;
        log.info("After method called with result {} and time taken by {} nanoseconds", result, endTime);
        return result;
    }
}
