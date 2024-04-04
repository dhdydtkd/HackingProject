package com.example.hackingproject.stock;

import com.example.hackingproject.BaseModel;
import com.example.hackingproject.dao.StockDAO;
import com.example.hackingproject.login.dto.LoginReq;
import com.example.hackingproject.stock.service.StockService;
import com.example.hackingproject.stock.service.StockIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/stock")
public class StockAPIController {
    @Autowired
    private StockService stockService;
    @Autowired
    private StockIndexService stockIndexService;


    @RequestMapping(method = RequestMethod.GET, path = "/stocklist")
    public BaseModel StockList(HttpServletRequest request, HttpServletResponse response) {
        BaseModel baseModel = new BaseModel();

        baseModel.setBody(stockService.getStockList());

        return baseModel;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/stock")
    public BaseModel getStock(HttpServletRequest request, HttpServletResponse response
            , @RequestParam(value = "stockCode")String stockCode) {
        BaseModel baseModel = new BaseModel();
        Map<String, Object> result = new HashMap<String,Object>();
        result.put("stockDetail",stockService.getStockDetail(stockCode));
        result.put("stockMinute",stockService.getStockMinute(stockCode));

        baseModel.setBody(result);

        return baseModel;
    }


    @RequestMapping(method = RequestMethod.GET, path = "/newstock")
    public BaseModel getNewStock(HttpServletRequest request, HttpServletResponse response
            , @RequestParam(value = "stockCode")String stockCode) {
        BaseModel baseModel = new BaseModel();
        Map<String, Object> result = new HashMap<String,Object>();
        result.put("stockNewDetail",stockService.getStockDetail(stockCode));

        baseModel.setBody(result);

        return baseModel;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/stockindex")
    public BaseModel getStockIndex(HttpServletRequest request, HttpServletResponse response) {
        BaseModel baseModel = new BaseModel();

        baseModel.setBody(stockIndexService.getStockIndexList());

        return baseModel;
    }



}
