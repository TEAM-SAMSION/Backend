package com.pawith.jwt.jwt;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JWTProperties.class)
public class ConfigurationPropertiesConfig {
}
