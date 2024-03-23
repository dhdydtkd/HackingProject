package com.example.hackingproject.file.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Getter
@Setter
public class FileReq {
    private String fileName;
    private long fileSize;
    private String fileUrl;
    private String fileSaveName;
}
