package com.petmory.log.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Pointcuts {


    @Pointcut("execution(* com.petmory..presentation.controller..*.*(..))")
    public void allController() {}

    @Pointcut("execution(* com.petmory..application.service..*.*(..))")
    public void allService() {
    }

}
