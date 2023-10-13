package com.pawith.authapplication.service.command.feign;

import com.pawith.authapplication.service.command.feign.response.GoogleUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "googleOAuth",url = "https://www.googleapis.com/oauth2/v3/userinfo")
public interface GoogleOAuthFeignClient {

    @GetMapping
    GoogleUserInfo getGoogleUserInfo(@RequestHeader("Authorization") String token);
}
