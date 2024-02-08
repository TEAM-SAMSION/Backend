package com.pawith.authdomain.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Getter
public class PrivateClaims {
    private final UserClaims userClaims;
    private final TokenType tokenType;

    public PrivateClaims(UserClaims userClaims, TokenType tokenType) {
        this.userClaims = userClaims;
        this.tokenType = tokenType;
    }

    public Map<String, Object> createClaimsMap() {
        return Map.of(
                JWTConsts.USER_CLAIMS, userClaims,
                JWTConsts.TOKEN_TYPE, tokenType.name()
        );
    }

    public static Map<String, Class<?>> getClaimsTypeDetailMap(){
        return Map.of(
                JWTConsts.USER_CLAIMS, PrivateClaims.UserClaims.class,
                JWTConsts.TOKEN_TYPE, TokenType.class
        );
    }

    @Getter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserClaims{
        @JsonProperty("user_id")
        private Long userId;
        private UserClaims(Long userId) {
            this.userId = userId;
        }

        public static UserClaims of(Long userId){
            return new UserClaims(userId);
        }

        public PrivateClaims createPrivateClaims(TokenType tokenType) {
            return new PrivateClaims(this, tokenType);
        }
    }
}
