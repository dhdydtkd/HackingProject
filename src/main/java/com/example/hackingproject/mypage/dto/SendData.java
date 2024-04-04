package com.example.hackingproject.mypage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendData {
    public String name;
    public String transfer_bankagency;
    public String account_number;
    public Integer price;

    public String send_id;
}
