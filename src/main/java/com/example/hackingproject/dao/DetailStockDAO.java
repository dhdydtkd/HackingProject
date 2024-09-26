package com.example.hackingproject.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.hackingproject.detailStock.vo.DetailStockVO;

@Mapper
public interface DetailStockDAO {
	void buyStock(DetailStockVO detailStockVO);
	void sellStock(DetailStockVO detailStockVO);
	DetailStockVO haveStock(DetailStockVO detailStockVO);
	void insertUser (DetailStockVO detailStockVO);
}
