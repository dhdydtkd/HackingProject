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
        System.out.println(request.getRequestURL());
        return false;
//        return super.preHandle(request, response, handler);
////        logger.info("================ URL 요청 전 인터셉터 ==================");
//        Cookie[] cookies = request.getCookies();
//        String JWTToken = null;
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals(HeaderJWTTokenName)) {
//                    JWTToken = cookie.getValue();
//                    break;
//                }
//            }
//        }
////        request.getRequestURL()
//        System.out.println("request.getRequestURL() = "+request.getRequestURL());
//        if(JWTToken==null||JWTToken==""){
//            System.out.println("JWTToken = null");
////            response.sendRedirect("/login");
//        }else{
//            if(jwtTokenUtil.validateToken(JWTToken)){
////            jwtTokenUtil.getUserIdFromToken(token);
////            jwtTokenUtil.validateTokenForUser(auth);
//                System.out.println("JWTToken에 데이터 있음");
//            }else{
//                System.out.println("JWTToken에 데이터 없음");
////            response.sendRedirect("/login");
//            }
//        }
//        return super.preHandle(request, response, handler);
    }
}