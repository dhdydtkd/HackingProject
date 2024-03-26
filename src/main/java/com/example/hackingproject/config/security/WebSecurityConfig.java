package com.example.hackingproject.config.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static String[] matchPatten = {
            "/**"
    };
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().
                authorizeRequests()
                .antMatchers(matchPatten).permitAll()
                .anyRequest().authenticated()
        ;
        //corsConfiguration.addAllowedOrigin("*");
    }

}
