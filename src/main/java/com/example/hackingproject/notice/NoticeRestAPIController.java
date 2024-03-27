package com.example.hackingproject.notice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class NoticeRestAPIController {

    @RequestMapping(value = "/test_notice", method = RequestMethod.GET)
    public ModelAndView notice(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("test_notice");
        return mav;
    }
}
