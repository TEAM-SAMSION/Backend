package com.pawith.authpresentation;

import com.pawith.authapplication.consts.AuthConsts;
import com.pawith.authapplication.dto.OAuthResponse;
import com.pawith.authapplication.dto.TokenReissueResponse;
import com.pawith.authapplication.service.LogoutUseCase;
import com.pawith.authapplication.service.OAuthUseCase;
import com.pawith.authapplication.service.ReissueUseCase;
import com.pawith.commonmodule.enums.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthUseCase oAuthUseCase;
    private final LogoutUseCase logoutUseCase;
    private final ReissueUseCase reissueUseCase;

    @GetMapping("/oauth/{provider}")
    public OAuthResponse oAuthLogin(@PathVariable Provider provider,
                                    @RequestHeader("Authorization") String accessToken,
                                    @RequestHeader(value = "RefreshToken", required = false) String refreshToken){
        return oAuthUseCase.oAuthLogin(provider, accessToken, refreshToken);
    }

    @PostMapping("/reissue")
    public TokenReissueResponse reissue(@RequestHeader(AuthConsts.REFRESH_TOKEN_HEADER) String refreshToken){
        return reissueUseCase.reissue(refreshToken);
    }


    @DeleteMapping("/logout")
    public void logout(@RequestHeader(AuthConsts.REFRESH_TOKEN_HEADER) String refreshToken){
        logoutUseCase.logoutAccessUser(refreshToken);
    }

}
