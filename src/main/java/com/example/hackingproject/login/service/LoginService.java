package com.example.hackingproject.login.service;

import com.example.hackingproject.login.vo.UserVO;
import com.example.hackingproject.dao.LoginDAO;
import com.example.hackingproject.login.dto.LoginReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("LoginService")
public class LoginService {
    @Autowired
    private LoginDAO loginDAO;

    public UserVO getUserInfo(LoginReq loginReq){
        UserVO user = loginDAO.getUserInfo(loginReq);
        if(user == null){
            return null;
        }else{
            loginDAO.updateUserLogin(user);
            return user;
        }

    }
}
