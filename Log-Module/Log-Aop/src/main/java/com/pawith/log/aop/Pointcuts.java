package com.pawith.log.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Pointcuts {


    @Pointcut("execution(* com.pawith.*presentation.*Controller.*(..))")
    public void allController() {}

    @Pointcut("execution(* com.pawith..service..*.*(..)) || execution(* com.pawith..service..*.*(..))")
    public void allService() {
    }

}
