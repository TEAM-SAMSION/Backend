package com.pawith.authapplication.service.oauth;

import com.pawith.authapplication.consts.AuthConsts;
import com.pawith.authapplication.dto.OAuthRequest;
import com.pawith.authapplication.dto.OAuthResponse;
import com.pawith.authapplication.dto.OAuthUserInfo;
import com.pawith.authapplication.exception.AuthException;
import com.pawith.authdomain.exception.AuthError;
import com.pawith.authdomain.jwt.JWTProvider;
import com.pawith.authdomain.jwt.PrivateClaims;
import com.pawith.authdomain.jwt.TokenType;
import com.pawith.authdomain.service.TokenSaveService;
import com.pawith.userdomain.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OAuthInvoker {
    private final List<AuthHandler> authHandlerList;
    private final JWTProvider jwtProvider;
    private final TokenSaveService tokenSaveService;
    private final ApplicationEventPublisher publisher;
//    private final OAuthModifyingService oAuthModifyingService;
//    private final OAuthQueryService oAuthQueryService;
//    private final OAuthSaveService oAuthSaveService;
    private final UserQueryService userQueryService;
    private final TransactionTemplate transactionTemplate;

    public OAuthResponse execute(OAuthRequest request){
        // oauth 정보가 없으면 생성
        // 생성하고 조회, email이 없어도 될지, user에서 email, provider는 제거
        // oauth에는 userId를 넣는다?
        // 있으면 그대로 진행
        final OAuthUserInfo oAuthUserInfo = attemptLogin(request);
        saveOAuthAndPublishEvent(request, oAuthUserInfo);
        return generateServerAuthenticationTokens(PrivateClaims.UserClaims.of(userQueryService.findByEmail(oAuthUserInfo.getEmail()).getId()));
    }

    private void saveOAuthAndPublishEvent(OAuthRequest request, OAuthUserInfo oAuthUserInfo) {
        transactionTemplate.execute(status -> {
            /**
             * OAuth 정보가 이미 존재하면, OAuth 정보를 조회하고 이메일 정보가 변경되면 UserEmailUpdateEvent를 발행한다.
             * OAuth 존재하지 않으면 새로운 OAuth 정보를 생성한다. 이후에 UserSignUpEvent를 발행하여 사용자의 가입을 알린다.
             */
//            if(oAuthQueryService.isOAuthExist(oAuthUserInfo.getSub())){
//                final OAuth oAuth = oAuthQueryService.findBySub(oAuthUserInfo.getSub());
//                oAuthModifyingService.
//            } else{
//                publisher.publishEvent(UserSignUpEvent.of(oAuthUserInfo.getUsername(), oAuthUserInfo.getEmail(), request.getProvider()));
//                oAuthSaveService.saveOAuthUser(
//                    OAuth.of(
//                        request.getProvider(),
//                        request.getRefreshToken(),
//                        oAuthUserInfo.getEmail(),
//                        oAuthUserInfo.getSub(),
//                        userQueryService.findByEmail(oAuthUserInfo.getEmail()).getId()
//                    )
//                );
//            }
//            return null;
            return null;
        });
    }

    private OAuthUserInfo attemptLogin(OAuthRequest request) {
        for (AuthHandler authHandler : authHandlerList) {
            if (authHandler.isAccessible(request)) {
                return authHandler.handle(request);
            }
        }
        throw new AuthException(AuthError.OAUTH_FAIL);
    }

    private OAuthResponse generateServerAuthenticationTokens(PrivateClaims.UserClaims userClaims) {
        final JWTProvider.Token token = jwtProvider.generateToken(userClaims);
        tokenSaveService.saveToken(token.refreshToken(), TokenType.REFRESH_TOKEN, userClaims.getUserId());
        final String accessToken = AuthConsts.AUTHENTICATION_TYPE_PREFIX + token.accessToken();
        final String refreshToken = AuthConsts.AUTHENTICATION_TYPE_PREFIX + token.refreshToken();
        return OAuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }
}
