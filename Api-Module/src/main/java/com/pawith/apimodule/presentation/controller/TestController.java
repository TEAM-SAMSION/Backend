package com.pawith.apimodule.presentation.controller;

import com.pawith.jwt.jwt.JWTProvider;
import com.pawith.jwt.jwt.TokenType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final JWTProvider jwtProvider;

    @GetMapping("/test")
    public String test() {
        return "연결 성공!";
    }

    @GetMapping("/jwt")
    public String createJWT(@RequestParam String email, @RequestParam String tokenType) {
        if(tokenType.equals(TokenType.ACCESS_TOKEN.name())){
            return jwtProvider.generateAccessToken(email);
        }
        if(tokenType.equals(TokenType.REFRESH_TOKEN.name())){
            return jwtProvider.generateRefreshToken(email);
        }
        return "잘못된 토큰 타입입니다.";
    }


    @GetMapping("/reissue")
    public String reIssue(@RequestParam String token, @RequestParam String tokenType){
        if(tokenType.equals(TokenType.ACCESS_TOKEN.name())){
            return jwtProvider.reIssueAccessToken(token);
        }
        if(tokenType.equals(TokenType.REFRESH_TOKEN.name())){
            return jwtProvider.reIssueRefreshToken(token);
        }
        return "잘못된 토큰 타입입니다.";
    }
}
