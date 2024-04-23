package com.example.hackingproject.mypage.service;


import com.example.hackingproject.dao.DetailStockDAO;
import com.example.hackingproject.dao.UserDAO;
import com.example.hackingproject.mypage.dto.MyUserData;
import com.example.hackingproject.mypage.dto.SendData;
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

    public Boolean AccountPriceCheck(String user_id, SendData sendJsonData){
        MyUserData myUserData = userDAO.getUserInfo(user_id);
        if(myUserData.getACCOUNT_BALANCE() >= sendJsonData.getPrice()){
            return true;
        }else{
            return false;
        }
    }
    public MyUserData SendAccountCheck(SendData sendJsonData){
        MyUserData sendUserData = userDAO.getSendAccount(sendJsonData);
        if(sendUserData!=null){
            return sendUserData;
        }else{
            return null;
        }
    }

    public MyUserData AccountPriceMinus(String user_id, SendData sendData){
        userDAO.AccountPriceMinus(sendData);
        return userDAO.getUserInfo(user_id);
    }

    public void AccountPricePlus(SendData sendData){
        userDAO.AccountPricePlus(sendData);
    }
}
