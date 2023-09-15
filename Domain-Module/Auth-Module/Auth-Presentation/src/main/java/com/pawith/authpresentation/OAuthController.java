package com.pawith.authpresentation;

import com.pawith.authapplication.dto.OAuthResponse;
import com.pawith.authapplication.dto.Provider;
import com.pawith.authapplication.service.OAuthUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthUseCase oAuthUseCase;

    @GetMapping("/oauth/{provider}")
    public OAuthResponse oAuthLogin(@PathVariable Provider provider, @RequestParam String accessToken){
        log.info("provider: {}, accessToken: {}", provider, accessToken);
        return oAuthUseCase.oAuthLogin(provider, accessToken);
    }

}
