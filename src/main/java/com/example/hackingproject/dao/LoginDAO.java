package com.example.hackingproject.dao;

import com.example.hackingproject.login.dto.LoginReq;
import com.example.hackingproject.login.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginDAO {

    UserVO getUserInfo(LoginReq loginReq);
    int updateUserLogin(UserVO user);
}
