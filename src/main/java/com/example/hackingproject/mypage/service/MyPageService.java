package com.example.hackingproject.mypage.service;


import com.example.hackingproject.dao.DetailStockDAO;
import com.example.hackingproject.dao.UserDAO;
import com.example.hackingproject.mypage.dto.MyUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("MyPageService")
public class MyPageService {

    @Autowired
    private UserDAO userDAO;

    public MyUserData getUserInfo(String user_id){

        MyUserData myUserData = userDAO.getUserInfo(user_id);
        Integer nowTotalPrice = userDAO.getNowStockTotalPrice(user_id);
        Integer buyTotalPrice = userDAO.getBuyStockTotalPrice(user_id);
        myUserData.setNowTotalPrice(nowTotalPrice);
        myUserData.setBuyTotalPrice(buyTotalPrice);

        return myUserData;
    }
    public MyUserData getUserStockInfo(String user_id){

        MyUserData myUserData = new MyUserData();
        Integer nowTotalPrice = userDAO.getNowStockTotalPrice(user_id);
        Integer buyTotalPrice = userDAO.getBuyStockTotalPrice(user_id);
        myUserData.setNowTotalPrice(nowTotalPrice);
        myUserData.setBuyTotalPrice(buyTotalPrice);

        return myUserData;
    }
}
