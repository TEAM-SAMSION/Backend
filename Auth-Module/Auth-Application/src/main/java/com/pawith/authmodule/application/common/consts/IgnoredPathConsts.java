package com.pawith.authmodule.application.common.consts;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IgnoredPathConsts {

    @Getter
    private static Map<String, HttpMethod> ignoredPath = Map.of(
        "/docs/**", HttpMethod.GET,
        "/oauth/**", HttpMethod.GET,
        "/jwt", HttpMethod.GET,
        "/actuator/**", HttpMethod.GET
    );

}