package com.bsahomework.bsagiphy.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Log4j2
@Component
public class UserRequestLoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("User request [" + request + "] " + "[" + request.getMethod() + "] "
                + request.getRequestURI() + getParameters(request));
        return true;
    }

    private String getParameters(HttpServletRequest request) {
        var posted = new StringBuilder();
        Enumeration<String> e = request.getParameterNames();
        if (e != null) {
            posted.append("?");
        }
        while (e != null && e.hasMoreElements()) {
            if (posted.length() > 1) {
                posted.append("&");
            }
            String curr = e.nextElement();
            posted.append(curr).append("=");
            posted.append(request.getParameter(curr));
        }
        return posted.toString();
    }

}
