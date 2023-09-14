package com.pawith.authpresentation.common.security;

import com.pawith.authpresentation.common.security.filter.JWTAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain chain(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.addFilterBefore(SecurityHandler.getJWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(SecurityHandler.getJWTEntryPoint(), JWTAuthenticationFilter.class);

        http.csrf().disable();

        http.authorizeHttpRequests( request ->{
            request.anyRequest().permitAll();
        });

        http.formLogin().disable();
        http.httpBasic().disable();
        http.logout().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

}
