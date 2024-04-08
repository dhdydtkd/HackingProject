package com.example.hackingproject.config.interceotor;

import com.example.hackingproject.common.JwtTokenUtil;
import com.example.hackingproject.common.error.AppException;
import com.example.hackingproject.common.error.ErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;


@Component
public class WebInterceptor extends HandlerInterceptorAdapter {

    // 요청 들어가기 전
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getSession().getAttribute("user_id") != null){
            // 이미 로그인 한 상태임.
            return true;
        }else{
            // 로그인 하지 않았지만 토큰 한번 체크 해야함.
            response.sendRedirect("/login");
            return false;
        }

    }
}