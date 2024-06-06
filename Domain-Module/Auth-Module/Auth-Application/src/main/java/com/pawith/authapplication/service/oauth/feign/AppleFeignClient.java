package com.pawith.authapplication.service.oauth.feign;

import com.pawith.authapplication.service.oauth.feign.response.Keys;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "appleClient", url = "https://appleid.apple.com/auth")
public interface AppleFeignClient {

    @GetMapping(value = "/keys")
    Keys getKeys();

    @GetMapping(value = "/token")
    String getAccessToken();
}
