package com.pawith.authapplication.service.command.feign;

import com.pawith.authapplication.service.command.feign.response.TokenInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoTokenOAuth",url = "https://kapi.kakao.com/v1/user/access_token_info")
public interface KakaoTokenOAuthFeignClient {
    @GetMapping
    TokenInfo getKakaoTokenInfo(@RequestHeader("Authorization") String token);
}
