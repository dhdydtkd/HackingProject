package com.example.hackingproject.notice;

import com.example.hackingproject.notice.dto.NoticeReq;
import com.example.hackingproject.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    //공지사항 목록
    @GetMapping("/notice")
    public String notice(Model model) {
        List<NoticeReq> noticeList = noticeService.getNoticeList();
        model.addAttribute("noticeList", noticeList);
        return "notice";
    }

    //공지사항 작성
    @GetMapping("/notice-write")
    public String getNoticeWritePage() {
        return "notice-write";
    }

    //공지사항 세부
    @GetMapping("/notice-detail")
    public ModelAndView getNoticeDetailPage(@RequestParam("noticeNo") int noticeNo) {
        ModelAndView mav = new ModelAndView();
        NoticeReq notice = noticeService.getNoticeByNo(noticeNo);
        mav.addObject("noticeTitle", notice.getNOTICE_TITLE());
        mav.addObject("noticeContext", notice.getNOTICE_CONTEXT());
        mav.setViewName("notice-detail");
        return mav;
    }


    //공지사항 제출
    @PostMapping("/submit-notice")
    public ModelAndView submitNotice(NoticeReq noticeReq, MultipartFile file) {
        noticeService.registerNotice(noticeReq, file);
        ModelAndView mav = new ModelAndView();
        List<NoticeReq> noticeList = noticeService.getNoticeList();
        mav.addObject("noticeList", noticeList);
        mav.setViewName("redirect:/notice");
        return mav;
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
