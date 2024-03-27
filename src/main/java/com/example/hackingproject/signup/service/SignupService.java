package com.example.hackingproject.signup.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.hackingproject.signup.userentity.UserEntity;

@Service
public class SignupService {
	private static final Logger logger = LoggerFactory.getLogger(SignupService.class);
    public void registerUser(UserEntity userEntity) {
        // UserEntity 객체의 데이터를 로그로 출력
        logger.info("Registering user: UserPw: {}, UserTelno: {}, UserName: {}, UserIdNo: {}, UserBank: {}, AccountNumber: {}, UserAgency: {}, AccessLevel: {}, AccountBalance: {}",
                userEntity.getUserPw(), userEntity.getUserTelno(), userEntity.getUserName(), userEntity.getUserIdNo(),
                userEntity.getUserBank(), userEntity.getAccountNumber(), userEntity.getUserAgency(), userEntity.getAccessLevel(), userEntity.getAccountBalance());
    
    
    }

}
