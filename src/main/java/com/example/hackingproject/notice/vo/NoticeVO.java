package com.example.hackingproject.notice.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
public class NoticeVO {
    public int NOTICE_NO;
    public String USER_ID = "admin";
    public String NOTICE_TITLE;
    public String NOTICE_CONTEXT;
    public Date NOTICE_DATE;
    public MultipartFile NOTICE_FILE;
    public String NOTICE_FILE_PATH;

    public NoticeVO() {
        this.NOTICE_DATE = new Date();
    }
}
