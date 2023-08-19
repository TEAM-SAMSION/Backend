package com.pawith.authmodule.application.common.config;

import com.pawith.authmodule.application.port.out.observer.subject.AbstractOAuthSubject;
import com.pawith.commonmodule.utill.ApplicationContextUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;

@Configuration
@DependsOn("applicationContextUtils")
public class OAuthConfig {

    @Bean
    public AbstractOAuthSubject abstractOAuthSubject(){
        final AbstractOAuthSubject abstractOAuthSubject = new AbstractOAuthSubject();
        final ApplicationContext applicationContext = ApplicationContextUtils.getApplicationContext();
        abstractOAuthSubject.initStrategy(applicationContext);
        return abstractOAuthSubject;
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
