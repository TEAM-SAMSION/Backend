package com.petmory.authmodule.config.security;

import com.petmory.authmodule.config.security.filter.JWTAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SecurityHandler {
    private static JWTAuthenticationFilter jwtAuthenticationFilter;

    public SecurityHandler(JWTAuthenticationFilter jwtAuthenticationFilter) {
        SecurityHandler.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    public static JWTAuthenticationFilter getJWTAuthenticationFilter(){
        return jwtAuthenticationFilter;
    }
}
