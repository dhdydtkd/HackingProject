package com.example.hackingproject.notice;

import com.example.hackingproject.notice.dto.NoticeReq;
import com.example.hackingproject.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

    //공지사항 목록
    @GetMapping("/notice")
    public String notice(Model model) {
        List<NoticeReq> noticeList = noticeService.getNoticeList();
        model.addAttribute("noticeList", noticeList);
        return "notice";
    }

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
        mav.setViewName("notice-detail");
        return mav;
    }

    // 파일 다운로드
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam int noticeNo, @RequestParam String fileName) throws IOException {
        NoticeReq notice = noticeService.getNoticeByNo(noticeNo);
        String noticeFilePath = notice.getNOTICE_FILE_PATH();

        // 파일 경로 확인용 로그 출력
        logger.info("파일 경로: {}", noticeFilePath);

        // 업로드된 디렉토리에서 파일 경로 가져오기
        Path file = Paths.get(noticeFilePath);
        Resource resource = new UrlResource(file.toUri());

        if (resource.exists() && resource.isReadable()) {
            // 파일이 존재하고 읽을 수 있는 경우
            logger.info("다운로드할 파일을 찾았습니다: {}", file);

            // 원본 파일명 가져오기
            String originalFileName = notice.getNOTICE_FILE_NAME();

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + originalFileName + "\"") // 원본 파일명 사용
                    .body(resource);
        } else {
            // 파일이 존재하지 않거나 읽을 수 없는 경우에 대한 예외 처리
            String errorMessage = "파일을 찾을 수 없습니다: " + file;
            logger.error(errorMessage);
            throw new FileNotFoundException(errorMessage);
        }
    }


    //공지사항 작성
    @GetMapping("/notice-write")
    public String getNoticeWritePage() {
        return "notice-write";
    }

    //공지사항 제출
    @PostMapping("/submit-notice")
    public ModelAndView submitNotice(NoticeReq noticeReq, @RequestParam("NOTICE_FILE") MultipartFile file) {
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
