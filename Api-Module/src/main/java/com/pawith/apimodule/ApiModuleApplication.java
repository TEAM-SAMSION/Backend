package com.pawith.apimodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class })
@ComponentScan(basePackages = {"com.pawith"})
@EntityScan(basePackages = {"com.pawith"})
public class ApiModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiModuleApplication.class, args);
    }

}
