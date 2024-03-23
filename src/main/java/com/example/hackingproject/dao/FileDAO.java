package com.example.hackingproject.dao;

import com.example.hackingproject.file.dto.FileReq;
import com.example.hackingproject.file.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileDAO {
    Integer uploadFile(List<FileReq> fileList);

    List<FileVO> getFileList();

    Integer fileDelete(Integer fileNo);
    FileReq fileFind(Integer fileNo);


}
