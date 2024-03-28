package com.example.hackingproject.stock;

import com.example.hackingproject.BaseModel;
import com.example.hackingproject.dao.StockDAO;
import com.example.hackingproject.login.dto.LoginReq;
import com.example.hackingproject.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.PrivateKey;

@RestController
@RequestMapping("/stock")
public class StockAPIController {
    @Autowired
    private StockService stockService;


    @RequestMapping(method = RequestMethod.GET, path = "/stocklist")
    public BaseModel StockList(HttpServletRequest request, HttpServletResponse response) {
        BaseModel baseModel = new BaseModel();

        baseModel.setBody(stockService.getStockList());

        return baseModel;
    }
}
