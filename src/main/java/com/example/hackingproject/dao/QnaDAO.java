package com.example.hackingproject.dao;


import com.example.hackingproject.qna.dto.QnaData;
import com.example.hackingproject.qna.dto.QnaReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaDAO {

    List<QnaData> getQnaList(String user_id);
    List<QnaData> getAllQnaList();
    void addQna(QnaReq qnaReq);
}

