package com.pawith.authmodule.service.command.feign;

import com.pawith.authmodule.service.command.feign.response.Keys;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "appleClient", url = "https://appleid.apple.com/auth")
public interface AppleFeignClient {

    @GetMapping(value = "/keys")
    Keys getKeys();

    @GetMapping(value = "/token")
    String getAccessToken();
}
