package com.pawith.authmodule.application.common.config;

import com.pawith.authmodule.application.port.out.command.OAuthInvoker;
import com.pawith.commonmodule.utill.ApplicationContextUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("applicationContextUtils")
public class OAuthConfig {

    @Bean
    public OAuthInvoker oAuthInvoker(){
        final OAuthInvoker oAuthInvoker = new OAuthInvoker();
        final ApplicationContext applicationContext = ApplicationContextUtils.getApplicationContext();
        oAuthInvoker.initStrategy(applicationContext);
        return oAuthInvoker;
    }

}
