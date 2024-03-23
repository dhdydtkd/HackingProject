package com.example.hackingproject.notice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeReq {
    public String title;
    public String id;
    public String context;
    public String createID;
    public String createDT;
    public String updateDT;
}
