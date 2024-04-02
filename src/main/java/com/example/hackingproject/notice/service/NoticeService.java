package com.example.hackingproject.notice.service;

import com.example.hackingproject.dao.NoticeDAO;
import com.example.hackingproject.notice.dto.NoticeReq;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeDAO noticeDAO;
    private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);

    @Value("${file.upload-dir}")
    private String uploadDir;

    public List<NoticeReq> getNoticeList() {
        return noticeDAO.getNoticeList();
    }

    public void registerNotice(NoticeReq noticeReq, @RequestParam(value = "file", required = false) MultipartFile file) {
        // 파일 업로드
        if (file != null && !file.isEmpty()) {
            try {
                //업로드할 파일명
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                String uuidFileName = UUID.randomUUID() + "_" + fileName;

                //업로드 경로
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                //파일 저장
                Path filePath = uploadPath.resolve(uuidFileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                //파일 경로를 NoticeReq 객체에 설정
                noticeReq.setNOTICE_FILE_PATH(filePath.toString());
                noticeReq.setNOTICE_FILE_NAME(fileName); // 원본 파일명

                logger.info("파일 업로드 성공: {}", filePath);
            } catch (IOException e) {
                logger.error("파일 업로드 실패: {}", e.getMessage());
            }
        } else {
            logger.warn("업로드할 파일이 없습니다.");
        }

        noticeReq.setNOTICE_DATE(new Date());
        noticeDAO.registerNotice(noticeReq);
    }

    public void modifyNotice(NoticeReq noticeReq) {
        noticeDAO.modifyNotice(noticeReq);
    }

    public void deleteNotice(int NOTICE_NO) {
        noticeDAO.deleteNotice(NOTICE_NO);
    }

    public NoticeReq getNoticeByNo(int NOTICE_NO) {
        return noticeDAO.getNoticeByNo(NOTICE_NO);
    }
}
