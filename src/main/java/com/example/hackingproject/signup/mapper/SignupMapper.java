package com.example.hackingproject.signup.mapper;

import com.example.hackingproject.signup.userentity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Mapper
@SpringBootApplication
@MapperScan("com.example.hackingproject.signup.mapper") 
public interface SignupMapper {
    void insertUser(UserEntity user);
}
