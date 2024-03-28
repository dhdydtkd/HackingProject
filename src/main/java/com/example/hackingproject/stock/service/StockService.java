package com.example.hackingproject.stock.service;


import com.example.hackingproject.dao.NoticeDAO;
import com.example.hackingproject.dao.StockDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("StockService")
public class StockService {

    @Autowired
    private StockDAO stockDAO;


}
