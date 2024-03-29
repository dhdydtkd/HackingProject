package com.example.hackingproject.dao;

import com.example.hackingproject.notice.dto.NoticeReq;
import java.util.List;

public interface NoticeDAO {
    List<NoticeReq> getNoticeList();
    void registerNotice(NoticeReq noticeReq);
    void modifyNotice(NoticeReq noticeReq);
    void deleteNotice(int NOTICE_NO);
    NoticeReq getNoticeByNo(int noticeNo);
}
