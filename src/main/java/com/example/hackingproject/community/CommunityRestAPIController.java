package com.example.hackingproject.community;


import com.example.hackingproject.community.service.CommunityService;
import com.example.hackingproject.community.vo.CommunityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class CommunityRestAPIController {

    @Autowired
    CommunityService communityService;

    @GetMapping("/community")
    public ModelAndView thymeleafTest() {
        ModelAndView modelAndView = new ModelAndView("templates/community");

        List<CommunityVO> communityList = communityService.getCommunityList();
        modelAndView.addObject("pageTitle", "커뮤니티");
        modelAndView.addObject("companyName", "루키증권");
        modelAndView.addObject("communityTitle", "커뮤니티");
        modelAndView.addObject("searchText", "");

//        modelAndView.addObject("name", "1111");
        modelAndView.addObject("datas", communityList);
        return modelAndView;
    }
}
