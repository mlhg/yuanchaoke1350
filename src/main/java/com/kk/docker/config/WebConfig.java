package com.kk.docker.config;

import com.kk.docker.interceptor.ApiAccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author yuanchaoke craeng@126.com
 * @Description //TODO
 * @Date  22:54 22:54
 * @Param
 * @return
 **/
@Component
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private ApiAccessInterceptor apiAccessInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加api权限拦截器拦截所有api
        registry.addInterceptor(apiAccessInterceptor).addPathPatterns("/api/v1/**");
    }
}
