package com.example.hackingproject.dao;

import com.example.hackingproject.stock.dto.StockIndexData;
import com.example.hackingproject.stock.vo.StockIndexVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockIndexDAO {
    StockIndexVO getStockIndex(String code);
    void updateStockIndex(StockIndexData index);

}
