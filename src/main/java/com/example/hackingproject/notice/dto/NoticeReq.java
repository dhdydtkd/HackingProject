package com.example.hackingproject.notice.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
public class NoticeReq {
    private int NOTICE_NO;
    private String USER_ID = "admin";
    private String NOTICE_TITLE;
    private String NOTICE_CONTEXT;
    private Date NOTICE_DATE;
    private String NOTICE_FILE_NAME; //파일명
    private MultipartFile NOTICE_FILE; //파일 내용
    private String NOTICE_FILE_PATH; //파일 경로
}
