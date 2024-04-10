package com.example.hackingproject.community;


import com.example.hackingproject.community.service.CommunityService;
import com.example.hackingproject.community.vo.CommunityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CommunityRestAPIController {

    @Autowired
    CommunityService communityService;

    @GetMapping("/community")
    public String thymeleafTest(Model model) {
        List<CommunityVO> communityList = communityService.getCommunityList();
        model.addAttribute("pageTitle", "커뮤니티");
        model.addAttribute("companyName", "루키증권");
        model.addAttribute("communityTitle", "커뮤니티");
        model.addAttribute("searchText", "");
        model.addAttribute("datas", communityList);
        return "templates/community"; // Thymeleaf 템플릿을 반환
    }

    @GetMapping("/community/search")
    public String thymeleafSearchTest(Model model, @RequestParam String searchText) {
//
        model.addAttribute("searchText", "T(java.time.LocalDateTime).now()"); // 취약한 코드${T(java.time.LocalDateTime).now()}
        return "templates/communitytest"; // Thymeleaf 템플릿을 반환
    }

}


