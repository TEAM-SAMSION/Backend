package com.pawith.authapplication.service.oauth.feign.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenInfo{
    @JsonAlias("app_id")
    private String appId;

    @JsonAlias("id")
    private Long id;
}