package com.example.hackingproject.notice.service;

import com.example.hackingproject.dao.NoticeDAO;
import com.example.hackingproject.notice.dto.NoticeReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeDAO noticeDAO;

    public List<NoticeReq> getNoticeList() {
        return noticeDAO.getNoticeList();
    }

    public void registerNotice(NoticeReq noticeReq, MultipartFile file) {
        // 파일 업로드 추가
        noticeDAO.registerNotice(noticeReq);
    }

    public void modifyNotice(NoticeReq noticeReq) {
        noticeDAO.modifyNotice(noticeReq);
    }

    public void deleteNotice(int NOTICE_NO) {
        noticeDAO.deleteNotice(NOTICE_NO);
    }

    public NoticeReq getNoticeByNo(int noticeNo) {
        return noticeDAO.getNoticeByNo(noticeNo);
    }
}
