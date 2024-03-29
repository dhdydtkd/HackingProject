package com.example.hackingproject.notice.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class NoticeReq {
    private String NOTICE_NO;
    private String USER_ID = "admin";
    private String NOTICE_TITLE;
    private String NOTICE_CONTEXT;
    private Date NOTICE_DATE;
    private byte[] NOTICE_FILE;

    public NoticeReq() {
        this.NOTICE_DATE = new Date();
    }
}
