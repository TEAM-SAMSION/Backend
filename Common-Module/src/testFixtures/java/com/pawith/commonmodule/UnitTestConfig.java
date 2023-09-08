package com.pawith.commonmodule;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({MockitoExtension.class})
@SpringJUnitConfig // https://stackoverflow.com/questions/69175844/spring-security-test-withmockuser-not-working
public @interface UnitTestConfig {
}
