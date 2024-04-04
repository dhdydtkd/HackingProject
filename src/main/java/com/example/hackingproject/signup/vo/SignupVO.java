package com.example.hackingproject.signup.vo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class SignupVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String UserId;
    private String userIdNo;
    private String userPw;
    private String userTelno;
    private String userName;
    private String userBirth;
    private String userBank;
    private String accountNumber;
    private String userAgency;
    private int accessLevel;
    private double accountBalance;

}
