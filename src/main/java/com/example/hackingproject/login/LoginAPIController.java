package com.example.hackingproject.login;

import com.example.hackingproject.BaseModel;
import com.example.hackingproject.common.JwtTokenUtil;
import com.example.hackingproject.common.ServerStateEnum;
import com.example.hackingproject.login.dto.LoginReq;
import com.example.hackingproject.login.service.LoginService;
import com.example.hackingproject.login.vo.UserVO;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginAPIController {

	

	
    @Value("${rsa_web_key}")
    private String RSA_WEB_KEY ; // 개인키 session key

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public BaseModel login(HttpServletRequest request, HttpServletResponse response
            , @RequestBody LoginReq loginReq) {
        BaseModel baseModel = new BaseModel();


        
        HttpSession session = request.getSession();
        PrivateKey privateKey = (PrivateKey) session.getAttribute(RSA_WEB_KEY);

        baseModel.setBody(loginService.getUserInfo(loginReq, privateKey, request));

        return baseModel;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/autologin")
    public BaseModel autoLogin(HttpServletRequest request, HttpServletResponse response) {
        BaseModel baseModel = new BaseModel();
        String bearerToken = request.getHeader("Authorization");
        String token = bearerToken.replaceFirst("Bearer ", "");

        try{
            String user_id = jwtTokenUtil.getUserIdFromToken(token);
            if(user_id != null){
                HttpSession session = request.getSession();
                session.setAttribute("user_id",user_id);
            }else{
                baseModel.setState(ServerStateEnum.TOKEN_VALIDATION);
            }

        }catch (ExpiredJwtException | MalformedJwtException e){
            baseModel.setState(ServerStateEnum.TOKEN_VALIDATION);
        }
        return baseModel;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/logout")
    public BaseModel logout(HttpServletRequest request, HttpServletResponse response) {
        BaseModel baseModel = new BaseModel();
        // 현재 요청에 대한 세션을 가져옴
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 세션을 만료시킴
            session.invalidate();
        }

        return baseModel;
    }

}
