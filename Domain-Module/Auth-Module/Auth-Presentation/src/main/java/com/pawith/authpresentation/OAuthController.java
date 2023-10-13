package com.pawith.authpresentation;

import com.pawith.authapplication.dto.OAuthResponse;
import com.pawith.authapplication.service.LogoutUseCase;
import com.pawith.commonmodule.enums.Provider;
import com.pawith.authapplication.service.OAuthUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthUseCase oAuthUseCase;
    private final LogoutUseCase logoutUseCase;

    @GetMapping("/oauth/{provider}")
    public OAuthResponse oAuthLogin(@PathVariable Provider provider, @RequestParam String accessToken){
        log.info("provider: {}, accessToken: {}", provider, accessToken);
        return oAuthUseCase.oAuthLogin(provider, accessToken);
    }

    @DeleteMapping("/logout")
    public void logout(@RequestHeader("RefreshToken") String refreshToken){
        logoutUseCase.logoutAccessUser(refreshToken);
    }

}
