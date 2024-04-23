package com.example.hackingproject.qna;

import com.example.hackingproject.mypage.dto.MyUserData;
import com.example.hackingproject.mypage.service.MyPageService;
import com.example.hackingproject.qna.dto.QnaData;
import com.example.hackingproject.qna.service.QnaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QnaRestController {

    @Autowired
    private QnaService qnaService;

    @Autowired
    private MyPageService userService;

    @RequestMapping(value = {"/qna"}, method = RequestMethod.GET)
    public ModelAndView qna(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        String user_id = (String)request.getSession().getAttribute("user_id");

        MyUserData myUserData = userService.getUserInfo(user_id);

        if(myUserData.getACCESS_LEVEL() == 1 ){
            mav.addObject("add_flag", false);
        }else{
            mav.addObject("add_flag", true);
        }

        mav.setViewName("qna");
        return mav;
    }

    @RequestMapping(value = {"/qnawrite"}, method = RequestMethod.GET)
    public ModelAndView qnawrite(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        String user_id = (String)request.getSession().getAttribute("user_id");

        MyUserData myUserData = userService.getUserInfo(user_id);

        mav.addObject("user_nm", myUserData.getUSER_NM());
        mav.addObject("user_id", myUserData.getUSER_ID());

        mav.setViewName("qna_write");
        return mav;
    }
}
