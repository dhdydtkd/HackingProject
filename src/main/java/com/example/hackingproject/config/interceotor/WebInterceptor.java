package com.example.hackingproject.config.interceotor;

import com.example.hackingproject.common.JwtTokenUtil;
import com.example.hackingproject.common.error.AppException;
import com.example.hackingproject.common.error.ErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;


@Component
public class WebInterceptor extends HandlerInterceptorAdapter {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String HEADER_TYPE = "x-accept-type";
    private final String HeaderJWTTokenName = "SKJWTToken";

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // 요청 들어가기 전
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        String JWTToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(HeaderJWTTokenName)) {
                    JWTToken = cookie.getValue();
                    break;
                }
            }
        }

        if(JWTToken==null||JWTToken==""){
            System.out.println("JWT 없음");
            response.sendRedirect("/login");
            return false;
        }else{
            if(jwtTokenUtil.validateToken(JWTToken)){
                // JWT 정상 인증
//            jwtTokenUtil.getUserIdFromToken(token);
//            jwtTokenUtil.validateTokenForUser(auth);
                System.out.println("JWT 정상 인증");
                return true;
            }else{
                // JWT 검증 실패
                System.out.println("JWT 검증 실패");
                response.sendRedirect("/login");
                return false;
            }
        }
    }
}