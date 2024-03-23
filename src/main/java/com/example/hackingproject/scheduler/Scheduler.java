package com.example.hackingproject.scheduler;

import com.example.hackingproject.dao.NoticeDAO;
import com.example.hackingproject.notice.dto.NoticeReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Autowired
    private NoticeDAO noticeDAO;

    @Value("${service_system_name}")
    private String systemName ; // 개인키 session key

    // 오후 8시 0분 0초에 실행
    @Scheduled(cron = "0 0 20 * * ?")
    public void noticeSch() {
        NoticeReq notice = new NoticeReq();
        int noticeIndex = noticeDAO.getAddNoticeIndex();
        notice.setTitle("게시판 테스트"+noticeIndex);
        notice.setContext("스케쥴러로 생성한 게시글 "+noticeIndex+"번 입니다.");
        notice.setCreateID(systemName);
        noticeDAO.addSchedulerNotice(notice);
    }
}
