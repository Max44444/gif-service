package com.bsahomework.bsagiphy.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
@Component
public class RequestHeadersInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        if (request.getHeader("X-BSA-GIPHY") == null) {
            log.warn(request.getRemoteAddr() + " tried to connect with missing X-BSA-GIPHY header");
            response.sendError(HttpStatus.FORBIDDEN.value(), "Missing X-BSA-GIPHY header");
            return false;
        }
        return true;
    }
}
