package com.example.hackingproject.mypage;

import com.example.hackingproject.common.JwtTokenUtil;
import com.example.hackingproject.mypage.dto.MyUserData;
import com.example.hackingproject.mypage.service.MyPageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MyPageRestAPIController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MyPageService myPageService;

    @RequestMapping(value = {"/mypage"}, method = RequestMethod.GET)
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        String JWTToken = jwtTokenUtil.GetJWTCookie(request);
        String user_id = jwtTokenUtil.getUserIdFromToken(JWTToken);
        MyUserData myUserData = myPageService.getUserInfo(user_id);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String myUserDataJson = objectMapper.writeValueAsString(myUserData);
            mav.addObject("myUserDataJson", myUserDataJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        mav.addObject("user_id", myUserData.getUSER_NM());
        mav.addObject("user_bank_name", myUserData.getUSER_BANK());
        mav.addObject("user_account_number", myUserData.getUSER_ACCOUNT_NUMBER());

        mav.setViewName("mypage");
        return mav;
    }
}
