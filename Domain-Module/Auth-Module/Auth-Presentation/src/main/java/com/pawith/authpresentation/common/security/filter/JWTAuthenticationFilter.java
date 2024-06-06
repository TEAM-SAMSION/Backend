package com.pawith.authpresentation.common.security.filter;

import com.pawith.authapplication.consts.AuthConsts;
import com.pawith.authapplication.consts.IgnoredPathConsts;
import com.pawith.authapplication.service.JWTExtractUserDetailsUseCase;
import com.pawith.authapplication.service.JWTExtractTokenUseCase;
import com.pawith.authapplication.service.JWTVerifyUseCase;
import com.pawith.authpresentation.common.security.JWTAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTVerifyUseCase jwtVerifyUseCase;
    private final JWTExtractUserDetailsUseCase<Long> jwtExtractUserDetailsUseCase;
    private final JWTExtractTokenUseCase jwtExtractTokenUseCase;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!isIgnoredPath(request)){
            final String tokenHeaderValue = getTokenFromHeader(request);
            final String accessToken = jwtExtractTokenUseCase.extractToken(tokenHeaderValue);
            jwtVerifyUseCase.validateToken(accessToken);
            final Long userId = jwtExtractUserDetailsUseCase.extract(accessToken);
            initAuthentication(new JWTAuthenticationToken(userId));
        }
        filterChain.doFilter(request, response);
    }


    private String getTokenFromHeader(HttpServletRequest request){
        String header = request.getHeader(AuthConsts.AUTHORIZATION);
        return StringUtils.hasText(header)? header : AuthConsts.EMPTY_HEADER;
    }

    private Boolean isIgnoredPath(HttpServletRequest request){
        final Set<String> ignoredPathURI = IgnoredPathConsts.getIgnoredPath().keySet();
        return ignoredPathURI.stream()
            .anyMatch(ignoredPath -> isMatchingPath(request, ignoredPath)&&isMatchingMethod(request, ignoredPath));
    }

    private Boolean isMatchingPath(HttpServletRequest request, String ignoredPathURI){
        final AntPathMatcher antPathMatcher = new AntPathMatcher();
        return antPathMatcher.matchStart(ignoredPathURI, request.getRequestURI());
    }

    private Boolean isMatchingMethod(HttpServletRequest request, String ignoredPathURI){
        return IgnoredPathConsts.getIgnoredPath().get(ignoredPathURI).matches(request.getMethod());
    }

    private void initAuthentication(final Authentication authentication){
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
