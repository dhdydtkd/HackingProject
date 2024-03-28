package com.example.hackingproject.stock;

import com.example.hackingproject.common.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class StockRestAPIController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = {"/main","/"}, method = RequestMethod.GET)
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();

//        mav.addObject("login", jwtTokenUtil.JWTTokenCheck(request));

        mav.setViewName("main_menu");
        return mav;
    }
}
