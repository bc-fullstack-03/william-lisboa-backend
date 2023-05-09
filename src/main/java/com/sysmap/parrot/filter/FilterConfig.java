package com.sysmap.parrot.filter;

import com.sysmap.parrot.jwt.JwtAuthenticationFilter;
import com.sysmap.parrot.jwt.JwtTokenUtil;
import com.sysmap.parrot.service.user.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class FilterConfig {

    private final JwtTokenUtil jwtTokenUtil;
    private UserServiceImpl userService;

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> authorizationFilter(){
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new JwtAuthenticationFilter(jwtTokenUtil,userService));

        registrationBean.addUrlPatterns("/api/v1/users","/api/v1/users/*","/api/v1/posts","/api/v1/posts/*");

        return registrationBean;
    }
}