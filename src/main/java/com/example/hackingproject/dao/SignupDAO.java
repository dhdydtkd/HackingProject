package com.example.hackingproject.dao;

import com.example.hackingproject.signup.vo.SignupVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SignupDAO {
	void insertUser(SignupVO user);
}
