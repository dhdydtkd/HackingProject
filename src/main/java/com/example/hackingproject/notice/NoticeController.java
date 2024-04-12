package com.example.hackingproject.notice;

import com.example.hackingproject.notice.dto.NoticeReq;
import com.example.hackingproject.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    @Value("${file.upload-dir}")
    private String uploadDir;

    //공지사항 세부
    @GetMapping("/notice-detail")
    public ModelAndView getNoticeDetailPage(@RequestParam("noticeNo") int NOTICE_NO) {
        ModelAndView mav = new ModelAndView();
        NoticeReq notice = noticeService.getNoticeByNo(NOTICE_NO);
        mav.addObject("noticeNo", NOTICE_NO);
        mav.addObject("noticeTitle", notice.getNOTICE_TITLE());
        mav.addObject("noticeDate", notice.getNOTICE_DATE());
        mav.addObject("noticeContext", notice.getNOTICE_CONTEXT());
        mav.addObject("noticeFileName", notice.getNOTICE_FILE_NAME());
        mav.addObject("noticeFilePath", notice.getNOTICE_FILE_PATH());
        mav.setViewName("noticedetail");
        return mav;
    }

    // 파일 다운로드
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam int noticeNo, @RequestParam String fileName) throws IOException {
        return noticeService.downloadFile(noticeNo, fileName);
    }

    //공지사항 작성
    @GetMapping("/noticewrite")
    public String getNoticeWritePage(HttpSession session) {
        Integer accessLevel = (Integer) session.getAttribute("access_level");
        if (accessLevel != null && accessLevel == 1) {
            System.out.println("Access level: " + accessLevel);
            return "noticewrite"; // 관리자인 경우 공지사항 작성 페이지 이동
        } else {
            return "redirect:/main"; // 관리자가 아닌 경우 메인 페이지로 이동
        }
    }

    //공지사항 제출
    @PostMapping("/submit-notice")
    public ModelAndView submitNotice(NoticeReq noticeReq, @RequestParam("NOTICE_FILE") MultipartFile file) {
        return noticeService.submitNotice(noticeReq, file);
    }

    //공지사항 수정
    @PutMapping("/notice/modify")
    public ModelAndView modifyNotice(NoticeReq noticeReq) {
        noticeService.modifyNotice(noticeReq);
        return new ModelAndView("redirect:/notice");
    }

    //공지사항 삭제
    @DeleteMapping("/notice/delete/{NOTICE_NO}")
    public ModelAndView deleteNotice(@PathVariable("NOTICE_NO") int noticeNo) {
        noticeService.deleteNotice(noticeNo);
        return new ModelAndView("redirect:/notice");
    }
}