package com.pawith.authmodule.config;

import com.pawith.authmodule.application.port.in.JWTExtractEmailUseCase;
import com.pawith.authmodule.application.port.in.JWTExtractTokenUseCase;
import com.pawith.authmodule.application.port.in.JWTVerifyUseCase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class FilterConfig {

    @Bean
    public JWTVerifyUseCase jwtVerifyUseCase(){
        return mock(JWTVerifyUseCase.class);
    }

    @Bean
    public JWTExtractEmailUseCase jwtExtractEmailUseCase(){
        return mock(JWTExtractEmailUseCase.class);
    }

    @Bean
    public JWTExtractTokenUseCase jwtExtractTokenUseCase(){
        return mock(JWTExtractTokenUseCase.class);
    }
}
