package com.example.hackingproject.detailStock;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.hackingproject.common.JwtTokenUtil;

@Controller
public class DetailStockRESTAPIController {
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
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
        jwtTokenUtil.getUserIdFromToken(JWTToken);
        System.out.println("id : "+ jwtTokenUtil.getUserIdFromToken(JWTToken));
        mav.addObject("id", jwtTokenUtil.getUserIdFromToken(JWTToken));
        mav.addObject("stockCode", stockCode);
        mav.addObject("stockName", stockName);
        
        mav.setViewName("detail_stock");
        return mav;
    }
}
