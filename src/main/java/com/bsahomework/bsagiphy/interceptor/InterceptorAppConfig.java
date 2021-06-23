package com.bsahomework.bsagiphy.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorAppConfig implements WebMvcConfigurer {

    @Autowired
    RequestHeadersInterceptor headersInterceptor;

    @Autowired
    UserRequestLoggerInterceptor loggerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(headersInterceptor);
        registry.addInterceptor(loggerInterceptor);
    }
}
