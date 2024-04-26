package com.example.hackingproject.login;

import com.example.hackingproject.common.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.*;
import java.security.spec.RSAPublicKeySpec;

@Controller
public class LoginRestAPIController {
	
	
    @Value("${rsa_web_key}")
    private String RSA_WEB_KEY ; // 개인키 session key

    @Value("${rsa_modulus_key}")
    private String RSAModulus ; // 개인키 session key
    @Value("${rsa_exponent_key}")
    private String RSAExponent ; // 개인키 session key

    @Value("${rsa_instance}")
    private String RSA_INSTANCE; // rsa transformation

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

//    @RequestMapping(value = "/test_login", method = RequestMethod.GET)
//    public ModelAndView testLogin(HttpServletRequest request, HttpServletResponse response) {
//        initRsa(request);
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("test_login");
//        return mav;
//    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {

    	
        String path = System.getProperty("user.dir");
        System.out.println("현재 작업 경로: " + path);


        
        initRsa(request);
        ModelAndView mav = new ModelAndView();
        Boolean loginFlag = jwtTokenUtil.JWTTokenCheck(request);
        if(loginFlag){
            mav.setViewName("main_menu");
        }{
            mav.setViewName("login");
        }

        return mav;
    }

    /**
     * rsa 공개키, 개인키 생성
     *
     * @param request
     */
    public void initRsa(HttpServletRequest request) {
        HttpSession session = request.getSession();

        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance(RSA_INSTANCE);
            generator.initialize(1024);

            KeyPair keyPair = generator.genKeyPair();
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_INSTANCE);
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            session.setAttribute(RSA_WEB_KEY, privateKey); // session에 RSA 개인키를 세션에 저장

            RSAPublicKeySpec publicSpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            String publicKeyModulus = publicSpec.getModulus().toString(16);
            String publicKeyExponent = publicSpec.getPublicExponent().toString(16);
            session.setAttribute(RSAModulus, publicKeyModulus);
            session.setAttribute(RSAExponent, publicKeyExponent);

            request.setAttribute(RSAModulus, publicKeyModulus); // rsa modulus 를 request 에 추가
            request.setAttribute(RSAExponent, publicKeyExponent); // rsa exponent 를 request 에 추가
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
