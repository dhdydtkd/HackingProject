package com.example.hackingproject.mypage;

import com.example.hackingproject.BaseModel;
import com.example.hackingproject.common.JwtTokenUtil;
import com.example.hackingproject.login.dto.LoginReq;
import com.example.hackingproject.mypage.dto.MyUserData;
import com.example.hackingproject.mypage.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.PrivateKey;

@RestController
@RequestMapping("/mypage")
public class MyPageAPIController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MyPageService myPageService;

    @RequestMapping(method = RequestMethod.POST, path = "/userinfo")
    public BaseModel login(HttpServletRequest request, HttpServletResponse response) {
        BaseModel baseModel = new BaseModel();
        String JWTToken = jwtTokenUtil.GetJWTCookie(request);
        String user_id = jwtTokenUtil.getUserIdFromToken(JWTToken);
        MyUserData myUserData = myPageService.getUserInfo(user_id);
        baseModel.setBody(myUserData);
        return baseModel;
    }
}
