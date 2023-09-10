package com.pawith.authmodule.common.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawith.commonmodule.exception.BusinessException;
import com.pawith.commonmodule.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTEntryPoint extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (BusinessException exception){
            handleException(response, exception);
        }
    }

    private void handleException(HttpServletResponse response, BusinessException exception) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.from(exception);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
