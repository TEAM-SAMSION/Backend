package com.pawith.authapplication.service.command.feign;

import com.pawith.authapplication.service.command.feign.response.KakaoUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoOAuth",url = "https://kapi.kakao.com/v2/user/me")
public interface KakaoOAuthFeignClient {

    @GetMapping
    KakaoUserInfo getKakaoUserInfo(@RequestHeader("Authorization") String token);
}
