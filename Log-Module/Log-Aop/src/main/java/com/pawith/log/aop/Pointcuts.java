package com.pawith.log.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Pointcuts {


    @Pointcut("execution(* com.pawith..adaptor.api..*.*(..)) || execution(* com.pawith..presentation.controller..*.*(..))")
    public void allController() {}

    @Pointcut("execution(* com.pawith..application.service..*.*(..)) || execution(* com.pawith..domain.service..*.*(..))")
    public void allService() {
    }

}
