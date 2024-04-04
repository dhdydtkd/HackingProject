package com.example.hackingproject.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.hackingproject.detailStock.vo.DetailStockVO;
import com.example.hackingproject.mypage.dto.MyUserData;

@Mapper
public interface DetailStockDAO {
	void buyStock(DetailStockVO detailStockVO);
	void sellStock(DetailStockVO detailStockVO);
	DetailStockVO haveStock(DetailStockVO detailStockVO);
	void insertUser (DetailStockVO detailStockVO);
	MyUserData getUserData (String user);
	void minusAccount(DetailStockVO detailStockVO);
	void plusAccount(DetailStockVO detailStockVO);
}
