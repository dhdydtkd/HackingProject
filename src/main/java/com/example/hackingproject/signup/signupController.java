package com.example.hackingproject.signup;

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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.hackingproject.signup.service.SignupService;
import com.example.hackingproject.signup.userentity.UserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@RequestMapping("/test_signup")

public class signupController {
	private static final Logger logger = LoggerFactory.getLogger(signupController.class);
    @Value("${rsa_web_key}")
    private String RSA_WEB_KEY ; // 개인키 session key

    @Autowired
	private SignupService signupService;
	
    @Autowired
    public void SignupController(SignupService signupService) {
        this.signupService = signupService;
    }
	@RequestMapping(path ="/signup_confirm", method = RequestMethod.POST)
	public BaseModel signupConfirm(HttpServletRequest request, HttpServletResponse response, @RequestBody UserEntity userEntity){
        BaseModel baseModel = new BaseModel();

        HttpSession session = request.getSession();
        PrivateKey privateKey = (PrivateKey) session.getAttribute(RSA_WEB_KEY);

        //String bodyJson = "{\"hello\": \"world\"}";
        baseModel.setBody(signupService.registerUser(userEntity,privateKey));
        return baseModel;
    }
}
