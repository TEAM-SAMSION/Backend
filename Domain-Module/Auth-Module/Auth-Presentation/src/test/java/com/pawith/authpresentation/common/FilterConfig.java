package com.pawith.authpresentation.common;

import com.pawith.authapplication.service.JWTExtractEmailUseCase;
import com.pawith.authapplication.service.JWTExtractTokenUseCase;
import com.pawith.authapplication.service.JWTVerifyUseCase;
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
