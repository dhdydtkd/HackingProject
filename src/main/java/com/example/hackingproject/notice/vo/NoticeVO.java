package com.example.hackingproject.notice.vo;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class NoticeVO {
    public String NOTICE_NO;
    public String USER_ID = "admin";
    public String NOTICE_TITLE;
    public String NOTICE_CONTEXT;
    public Date NOTICE_DATE;
    public byte[] NOTICE_FILE;

    public NoticeVO() {
        this.NOTICE_DATE = new Date();
    }
}
