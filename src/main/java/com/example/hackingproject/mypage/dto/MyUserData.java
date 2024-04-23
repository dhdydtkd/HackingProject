package com.example.hackingproject.mypage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyUserData {

    public String USER_ID;
    public String USER_NM;
    public String USER_BANK;
    public String USER_ACCOUNT_NUMBER;
    public Integer ACCOUNT_BALANCE;
    public Integer ACCESS_LEVEL;

    public Integer nowTotalPrice;
    public Integer buyTotalPrice;

}
