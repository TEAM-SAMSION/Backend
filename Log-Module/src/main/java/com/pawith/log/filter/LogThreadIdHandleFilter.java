package com.pawith.log.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawith.log.aop.LogTrace;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Order(Integer.MIN_VALUE)
@Component
@RequiredArgsConstructor
public class LogThreadIdHandleFilter implements Filter {

    private final ObjectMapper objectMapper;
    private final LogTrace logTrace;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logTrace.configThreadId();
        log.info(buildRequestInfoMessage((HttpServletRequest) request));
        chain.doFilter(request, response);
        logTrace.clearTheadId();
    }

    private String buildRequestInfoMessage(HttpServletRequest request) throws JsonProcessingException {
        final RequestInfoFormat requestInfoFormat = RequestInfoFormat.builder()
                .threadId(logTrace.getThreadId())
                .url(request.getRequestURI())
                .method(request.getMethod())
//                .ip(httpServletRequest.getRemoteAddr())
                .build();
        return objectMapper.writeValueAsString(requestInfoFormat);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
