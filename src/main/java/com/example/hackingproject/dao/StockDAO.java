package com.example.hackingproject.dao;

import com.example.hackingproject.notice.dto.NoticeReq;
import com.example.hackingproject.stock.dto.StockData;
import com.example.hackingproject.stock.dto.StockHourData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StockDAO {

    Integer addSchedulerStock(StockData base_stock);
    void deleteSchedulerStock();

    StockHourData getStock1HourData(Integer offSet);
//    List<User> selectUsersByIdList(List<Integer> idList);


}
