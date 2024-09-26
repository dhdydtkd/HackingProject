package com.example.hackingproject.login;

import com.example.hackingproject.BaseModel;
import com.example.hackingproject.login.dto.LoginReq;
import com.example.hackingproject.login.service.LoginService;
import com.example.hackingproject.login.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginAPIController {

    @Value("${rsa_web_key}")
    private String RSA_WEB_KEY ; // 개인키 session key

    @Autowired
    private LoginService loginService;

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public BaseModel login(HttpServletRequest request, HttpServletResponse response
            , @RequestBody LoginReq loginReq) {
        BaseModel baseModel = new BaseModel();

        HttpSession session = request.getSession();
        PrivateKey privateKey = (PrivateKey) session.getAttribute(RSA_WEB_KEY);

//        UserDetails userDetails = '';
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        request.getSession().set?
        baseModel.setBody(loginService.getUserInfo(loginReq, privateKey));
        return baseModel;
    }


}
