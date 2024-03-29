package com.example.hackingproject.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.hackingproject.signup.userentity.UserEntity;

@Mapper
public interface SignupDAO {
	void insertUser(UserEntity user);
}
