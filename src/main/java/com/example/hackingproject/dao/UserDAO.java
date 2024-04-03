package com.example.hackingproject.dao;

import com.example.hackingproject.mypage.dto.MyUserData;
import com.example.hackingproject.stock.vo.StockVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {

    MyUserData getUserInfo(String user_id);
    Integer getNowStockTotalPrice(String user_id);
    Integer getBuyStockTotalPrice(String user_id);
}
