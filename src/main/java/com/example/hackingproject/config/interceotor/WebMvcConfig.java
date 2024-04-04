package com.example.hackingproject.config.interceotor;

import com.example.hackingproject.common.JwtTokenUtil;
//import com.example.hackingproject.config.security.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    public static String[] matchPatten = {
            "/"
            , "/image/**"
            , "/css/**"
            , "/js/**"
            , "/login"
            , "/main"
            , "/login/login"
            , "/signup"
            , "/signup/signup_confirm"
            , "/stock/stocklist"
            , "/stock/stockindex"
            , "/favicon.ico"
            , "/error"
//            , "/**"
    };

    @Autowired
    private WebInterceptor webInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webInterceptor).addPathPatterns("/**")
                .excludePathPatterns(matchPatten);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
    }
    
}