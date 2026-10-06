package com.paf.pix_qrcode_generation.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter extends OncePerRequestFilter {

    public static final String HEADER = "X-Request-Id";
    public static final String MDC_KEY = "requestId";

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/actuator");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        String requestId = request.getHeader(HEADER);

        if (requestId == null || requestId.isBlank() || requestId.length() > 64) {
            requestId = UUID.randomUUID().toString();
        }

        MDC.put(MDC_KEY, requestId);

        request.setAttribute(HEADER, requestId);

        response.setHeader(HEADER, requestId);

        long start = System.nanoTime();

        try {
            chain.doFilter(request, response);
        } finally {
            long ms = (System.nanoTime() - start) / 1_000_000;
            int status = response.getStatus();

            String line = "{} {} -> {} ({} ms)";

            if (status >= 500) {
                log.error(line, request.getMethod(), request.getRequestURI(), status, ms);
            } else if (status >= 400) {
                log.warn(line, request.getMethod(), request.getRequestURI(), status, ms);
            } else {
                log.info(line, request.getMethod(), request.getRequestURI(), status, ms);
            }

            MDC.remove(MDC_KEY);
        }
    }
}