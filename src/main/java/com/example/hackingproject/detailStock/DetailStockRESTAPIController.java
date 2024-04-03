package com.example.hackingproject.detailStock;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.hackingproject.common.JwtTokenUtil;

import java.security.PrivateKey;

@Controller
public class DetailStockRESTAPIController {
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${rsa_modulus_key}")
    private String RSAModulus ; // 개인키 session key
    @Value("${rsa_exponent_key}")
    private String RSAExponent ; // 개인키 session key
	
    @RequestMapping(value = "/detailstock", method = RequestMethod.GET)
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response
    , @RequestParam(value = "stockCode")String stockCode
    , @RequestParam(value = "stockName")String stockName) {
        ModelAndView mav = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        String JWTToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("SKJWTToken")) {
                    JWTToken = cookie.getValue();
                }
            }
        }
        HttpSession session = request.getSession();
        String RSA_Modulus = (String)session.getAttribute(RSAModulus);
        String RSA_Exponent = (String)session.getAttribute(RSAExponent);
        request.setAttribute(RSAModulus, RSA_Modulus); // rsa modulus 를 request 에 추가
        request.setAttribute(RSAExponent, RSA_Exponent); // rsa exponent 를 request 에 추가
        System.out.println("detailstock");
        System.out.println(RSA_Modulus);
        System.out.println(RSA_Exponent);

        mav.addObject("id", jwtTokenUtil.getUserIdFromToken(JWTToken));
        mav.addObject("stockCode", stockCode);
        mav.addObject("stockName", stockName);
        
        mav.setViewName("detail_stock");
        return mav;
    }
}
