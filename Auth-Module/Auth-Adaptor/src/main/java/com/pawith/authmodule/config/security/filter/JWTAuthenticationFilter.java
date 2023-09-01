package com.pawith.authmodule.config.security.filter;

import com.pawith.authmodule.application.common.consts.AuthConsts;
import com.pawith.authmodule.application.common.consts.IgnoredPathConsts;
import com.pawith.authmodule.application.common.exception.InvalidAuthorizationTypeException;
import com.pawith.authmodule.application.port.in.JWTExtractEmailUseCase;
import com.pawith.authmodule.application.port.in.JWTExtractTokenUseCase;
import com.pawith.authmodule.application.port.in.JWTVerifyUseCase;
import com.pawith.authmodule.config.security.JWTAuthenticationToken;
import com.pawith.commonmodule.exception.Error;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTVerifyUseCase jwtVerifyUseCase;
    private final JWTExtractEmailUseCase jwtExtractEmailUseCase;
    private final JWTExtractTokenUseCase jwtExtractTokenUseCase;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!isIgnoredPath(request)){
            final String tokenHeaderValue = getTokenFromHeader(request);
            final String accessToken = jwtExtractTokenUseCase.extractToken(tokenHeaderValue);
            jwtVerifyUseCase.validateToken(accessToken);
            final String email = jwtExtractEmailUseCase.extractEmail(accessToken);
            initAuthentication(new JWTAuthenticationToken(email));
        }
        filterChain.doFilter(request, response);
    }


    private String getTokenFromHeader(HttpServletRequest request){
        String header = request.getHeader(AuthConsts.AUTHORIZATION);
        if(StringUtils.hasText(header)) return header;
        throw new InvalidAuthorizationTypeException(Error.EMPTY_AUTHORIZATION_HEADER);
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
