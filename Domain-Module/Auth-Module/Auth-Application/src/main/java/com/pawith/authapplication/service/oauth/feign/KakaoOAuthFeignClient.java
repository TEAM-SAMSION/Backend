package com.pawith.authapplication.service.oauth.feign;

import com.pawith.authapplication.service.oauth.feign.response.KakaoUserInfo;
import com.pawith.authapplication.service.oauth.feign.response.TokenInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoOAuth", url = "https://kapi.kakao.com")
public interface KakaoOAuthFeignClient {

    @GetMapping("/v2/user/me")
    KakaoUserInfo getKakaoUserInfo(@RequestHeader("Authorization") String token);

    @GetMapping("/v1/user/access_token_info")
    TokenInfo getKakaoTokenInfo(@RequestHeader("Authorization") String token);
}
