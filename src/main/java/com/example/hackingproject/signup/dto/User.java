package com.example.hackingproject.signup.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class User {
    private String userPw;
    private String userTelno;
    private String userName;
    private String userIdNo;
    private String userBank;
    private String accountNumber;
    private String userAgency;
    private int accessLevel;
    private double accountBalance;
}
