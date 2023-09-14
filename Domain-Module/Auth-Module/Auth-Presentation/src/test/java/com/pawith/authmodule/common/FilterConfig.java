package com.pawith.authmodule.common;

import com.pawith.authmodule.service.JWTExtractEmailUseCase;
import com.pawith.authmodule.service.JWTExtractTokenUseCase;
import com.pawith.authmodule.service.JWTVerifyUseCase;
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
