package com.example.hackingproject.mypage;

import com.example.hackingproject.common.JwtTokenUtil;
import com.example.hackingproject.mypage.dto.MyUserData;
import com.example.hackingproject.mypage.service.MyPageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class MyPageRestAPIController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MyPageService myPageService;

    @Value("${rsa_modulus_key}")
    private String RSAModulus ; // 개인키 session key
    @Value("${rsa_exponent_key}")
    private String RSAExponent ; // 개인키 session key

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

        mav.addObject("user_nm", myUserData.getUSER_NM());
        mav.addObject("user_bank_name", myUserData.getUSER_BANK());
        mav.addObject("user_account_number", myUserData.getUSER_ACCOUNT_NUMBER());

        mav.setViewName("mypage");
        return mav;
    }

    @RequestMapping(value = {"/mypage/transfer"}, method = RequestMethod.GET)
    public ModelAndView transfer(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        HttpSession session = request.getSession();
        String RSA_Modulus = (String)session.getAttribute(RSAModulus);
        String RSA_Exponent = (String)session.getAttribute(RSAExponent);
        request.setAttribute(RSAModulus, RSA_Modulus); // rsa modulus 를 request 에 추가
        request.setAttribute(RSAExponent, RSA_Exponent); // rsa exponent 를 request 에 추가

        mav.setViewName("transfer");
        return mav;
    }
}
