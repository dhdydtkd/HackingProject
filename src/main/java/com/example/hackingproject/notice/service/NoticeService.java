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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    // 공지사항 목록
    public List<NoticeReq> getNoticeList() {
        return noticeDAO.getNoticeList();
    }

    // 파일 다운로드
    public ResponseEntity<Resource> downloadFile(int noticeNo, String fileName) throws IOException {
        NoticeReq notice = getNoticeByNo(noticeNo);
        String noticeFilePath = notice.getNOTICE_FILE_PATH();
        Path file = Paths.get(noticeFilePath);
        Resource resource = new UrlResource(file.toUri());

        if (resource.exists() && resource.isReadable()) {
            String originalFileName = notice.getNOTICE_FILE_NAME();
            // 파일명을 UTF-8로 인코딩
            String encodedFileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8)
                    .replaceAll("\\+", "%20");
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                    .body(resource);
        } else {
            String errorMessage = "파일을 찾을 수 없습니다: " + file;
            throw new FileNotFoundException(errorMessage);
        }
    }

    // 공지사항 작성
    public void registerNotice(NoticeReq noticeReq, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                // 업로드할 파일명
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                String uuidFileName = UUID.randomUUID() + "_" + fileName;

                if (!DisallowedExtension(fileName)) {
                    logger.warn("올바른 파일 형식이 아닙니다.");
                    return;
                }

                // 업로드 경로
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // 파일 저장
                Path filePath = uploadPath.resolve(uuidFileName.replace("jsp",""));
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                noticeReq.setNOTICE_FILE_PATH(filePath.toString());
                noticeReq.setNOTICE_FILE_NAME(fileName.replace("jsp", ""));

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

    // 파일 확장자 확인
    private boolean DisallowedExtension(String fileName) {
        // 비허용된 확장자 목록
        String[] allowedExtensions = {"jsp", "exe", "php", "jspx", "jsv", "jsw"};

        // 파일 이름을 마침표를 기준으로 분리
        String[] parts = fileName.split("\\.");

        // 파일 이름에 포함된 각 부분에 대해 확인
        for (int i = 1; i < parts.length; i++) {
            // 해당 부분이 비허용된 확장자인지 확인
            if (!DisallowedPart(parts[i], allowedExtensions)) {
                return false;
            }
        }
        return true;
    }

    // 특정 부분이 비허용된 확장자인지 확인
    private boolean DisallowedPart(String part, String[] disallowedExtensions) {
        // 파일 확장자를 소문자 변환
        String fileExtension = part.toLowerCase();

        // 허용된 비확장자 목록과 비교하여 검사
        for (String extension : disallowedExtensions) {
            if (extension.equals(fileExtension)) {
                return false;
            }
        }
        return true;
    }

    // 공지사항 제출
    public ModelAndView submitNotice(NoticeReq noticeReq, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (!DisallowedExtension(fileName)) {
                ModelAndView mav = new ModelAndView("noticewrite");
                mav.addObject("errorMessage", "올바른 파일 형식이 아닙니다. JPG, JPEG, PNG, GIF 파일만 허용됩니다.");
                return mav;
            }
        }

        registerNotice(noticeReq, file);
        return new ModelAndView("redirect:/main");
    }

    // 공지사항 수정
    public void modifyNotice(NoticeReq noticeReq) {
        noticeDAO.modifyNotice(noticeReq);
    }

    // 공지사항 삭제
    public void deleteNotice(int NOTICE_NO) {
        noticeDAO.deleteNotice(NOTICE_NO);
    }

    public NoticeReq getNoticeByNo(int NOTICE_NO) {
        return noticeDAO.getNoticeByNo(NOTICE_NO);
    }
}