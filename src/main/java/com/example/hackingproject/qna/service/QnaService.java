package com.example.hackingproject.qna.service;

import com.example.hackingproject.dao.QnaDAO;
import com.example.hackingproject.mypage.dto.MyUserData;
import com.example.hackingproject.qna.dto.QnaData;
import java.util.List;

import com.example.hackingproject.qna.dto.QnaReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("QnaService")
public class QnaService {

    @Autowired
    private QnaDAO qnaDAO;

    public List<QnaData> getQnaList(MyUserData myUserData, String user_id){

        List<QnaData> QnaDataList = null;

        if(myUserData.getACCESS_LEVEL() == 1){
            QnaDataList = qnaDAO.getAllQnaList();
        }else{
            QnaDataList = qnaDAO.getQnaList(user_id);
        }

        return QnaDataList;
    }

    public void addQna(MyUserData myUserData, QnaReq qnaReq){

        qnaReq.setCreate_id(myUserData.getUSER_ID());
        qnaReq.setCreate_nm(myUserData.getUSER_NM());

        qnaDAO.addQna(qnaReq);
    }
}
