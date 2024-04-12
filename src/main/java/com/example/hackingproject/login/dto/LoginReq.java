package com.example.hackingproject.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReq {

    private String user_id;
    private String user_pw;
    private Boolean auto_login_flag;
}
