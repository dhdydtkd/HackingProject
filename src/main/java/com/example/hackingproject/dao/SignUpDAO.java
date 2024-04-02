package com.example.hackingproject.dao;

import com.example.hackingproject.signup.dto.SignUpReq;
import com.example.hackingproject.signup.vo.SignUpVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SignUpDAO {
    SignUpVO getUserInfo(SignUpReq signUpReq);
    int updateUserLogin(SignUpVO user);
}
