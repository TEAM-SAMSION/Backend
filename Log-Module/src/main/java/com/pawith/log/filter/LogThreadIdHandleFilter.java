package com.pawith.log.filter;

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
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final RequestInfoFormat requestInfoFormat = RequestInfoFormat.builder()
                .threadId(logTrace.getThreadId())
                .url(httpServletRequest.getRequestURI())
                .method(httpServletRequest.getMethod())
//                .ip(httpServletRequest.getRemoteAddr())
                .build();
        final String requestInfo = objectMapper.writeValueAsString(requestInfoFormat);
        log.info(requestInfo);
        chain.doFilter(request, response);
        logTrace.clearTheadId();
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
