package com.example.hackingproject.login.vo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO {
    private String USER_ID;
    private String USER_PW;
    private String USER_NM;

    private Integer ACCESS_LEVEL;

    public Integer getAccessLevel() {
        return this.ACCESS_LEVEL;
    }
}
