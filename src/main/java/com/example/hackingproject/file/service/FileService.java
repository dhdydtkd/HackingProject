package com.example.hackingproject.file.service;

import com.example.hackingproject.dao.FileDAO;
import com.example.hackingproject.file.dto.FileReq;
import com.example.hackingproject.file.vo.FileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.UUID;

@Service("FileService")
public class FileService {

    @Autowired
    private FileDAO fileDAO;

    @Value("${file.dir}")
    private String fileDir;

    public List<FileVO> getFileList(){
        return fileDAO.getFileList();
    }

    public FileReq fileFind(Integer fileNo){
        return fileDAO.fileFind(fileNo);
    }
    public Integer fileDelete(Integer fileNo){
        FileReq fileData = fileDAO.fileFind(fileNo);

        File file = new File(fileData.getFileUrl());
        if(file.exists()) {
            file.delete();
        }
        return fileDAO.fileDelete(fileNo);
    }

    public Boolean uploadFile(MultipartFile[] multipartFiles) {

        if (multipartFiles.length<1) {
            return false;
        }

        List<FileReq> fileList = new ArrayList<FileReq>();
        for(int i =0 ;i<multipartFiles.length;i++){
            FileReq file = new FileReq();
            file.setFileName(multipartFiles[i].getOriginalFilename());
            file.setFileSize(multipartFiles[i].getSize());

            LocalDate now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String toDay = now.format(formatter);
            folderCheck(toDay);

            String uuid = UUID.randomUUID().toString();
            String extension = file.getFileName().substring(file.getFileName().lastIndexOf("."));
            String savedName = uuid + extension;
            file.setFileSaveName(savedName);
            String savedPath = fileDir+toDay+File.separator+ savedName;

            // 실제로 로컬에 uuid를 파일명으로 저장
            try {
                multipartFiles[i].transferTo(new File(savedPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            file.setFileUrl(savedPath);
            fileList.add(file);
        }

        Integer result = fileDAO.uploadFile(fileList);

        return result > 0;
    }

    public void folderCheck(String toDay){
        File uploadFoler = new File(fileDir,toDay);
        if(!uploadFoler.exists()){
            uploadFoler.mkdir();
        }
    }


}
