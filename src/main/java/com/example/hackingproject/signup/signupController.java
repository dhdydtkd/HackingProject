package com.example.hackingproject.signup;

import com.example.hackingproject.BaseModel;
import com.example.hackingproject.signup.service.SignupService;
import com.example.hackingproject.signup.vo.SignupVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.PrivateKey;


@RestController
@RequestMapping("/signup")

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
	public BaseModel signupConfirm(HttpServletRequest request, HttpServletResponse response, @RequestBody SignupVO signupVO){
        BaseModel baseModel = new BaseModel();

        HttpSession session = request.getSession();
        PrivateKey privateKey = (PrivateKey) session.getAttribute(RSA_WEB_KEY);

        //String bodyJson = "{\"hello\": \"world\"}";
        baseModel.setBody(signupService.registerUser(signupVO,privateKey));
        return baseModel;
    }
}
