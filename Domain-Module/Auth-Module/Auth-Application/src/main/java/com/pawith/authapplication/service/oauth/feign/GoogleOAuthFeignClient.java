package com.pawith.authapplication.service.oauth.feign;

import com.pawith.authapplication.service.oauth.feign.response.GoogleUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "googleOAuth",url = "https://www.googleapis.com/userinfo/v2/me")
public interface GoogleOAuthFeignClient {

    @GetMapping
    GoogleUserInfo getGoogleUserInfo(@RequestHeader("Authorization") String token);
}
