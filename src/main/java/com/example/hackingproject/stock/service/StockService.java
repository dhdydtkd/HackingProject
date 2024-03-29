package com.example.hackingproject.stock.service;


import com.example.hackingproject.common.vo.TableSearchVO;
import com.example.hackingproject.dao.NoticeDAO;
import com.example.hackingproject.dao.StockDAO;
import com.example.hackingproject.notice.vo.NoticeVO;
import com.example.hackingproject.stock.vo.StockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("StockService")
public class StockService {

    @Autowired
    private StockDAO stockDAO;

    public List<StockVO> getStockList(){
        return stockDAO.getStockList();
    }

    public StockVO getStockDetail(String stockCode){

        return stockDAO.getStockDetail(stockCode);
    }

    public List<StockVO> getStockMinute(String stockCode){

        return stockDAO.getStockMinute(stockCode);
    }

}
