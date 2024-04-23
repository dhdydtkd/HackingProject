package com.example.hackingproject.qna;

import com.example.hackingproject.BaseModel;
import com.example.hackingproject.login.dto.LoginReq;
import com.example.hackingproject.mypage.dto.MyUserData;
import com.example.hackingproject.mypage.service.MyPageService;
import com.example.hackingproject.qna.dto.QnaData;
import com.example.hackingproject.qna.dto.QnaReq;
import com.example.hackingproject.qna.service.QnaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/qna")
public class QnaController {

    @Autowired
    private QnaService qnaService;

    @Autowired
    private MyPageService userService;

    @RequestMapping(method = RequestMethod.POST, path = "/myqna")
    public BaseModel myQNA(HttpServletRequest request, HttpServletResponse response) {
        BaseModel baseModel = new BaseModel();
        String user_id = (String)request.getSession().getAttribute("user_id");
        MyUserData myUserData = userService.getUserInfo(user_id);
        List<QnaData> QnaDataList = qnaService.getQnaList(myUserData, user_id);
        baseModel.setBody(QnaDataList);
        return baseModel;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/qnaadd")
    public BaseModel QnaAdd(HttpServletRequest request, HttpServletResponse response
            , @RequestBody QnaReq qnaReq) {
        BaseModel baseModel = new BaseModel();
        String user_id = (String)request.getSession().getAttribute("user_id");
        MyUserData myUserData = userService.getUserInfo(user_id);
        qnaService.addQna(myUserData, qnaReq);

        return baseModel;
    }
}
