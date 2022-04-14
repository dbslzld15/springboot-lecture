package org.prgrms.kdt.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointCut {
    @Pointcut("execution(public * org.prgrms.kdt..*Service.*(..))")
    public void servicePublicMethodPointCut() {};

    @Pointcut("execution(* org.prgrms.kdt..*Repository.*(..))")
    public void repositoryMethodPointCut() {};

    @Pointcut("execution(* org.prgrms.kdt..*Repository.insert(..))")
    public void repositoryInsertPointCut() {};
}
