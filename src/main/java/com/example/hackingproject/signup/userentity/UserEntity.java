package com.example.hackingproject.signup.userentity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserEntity {
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
