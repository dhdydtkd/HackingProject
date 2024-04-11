package com.example.hackingproject.community;


import com.example.hackingproject.community.service.CommunityService;
import com.example.hackingproject.community.vo.CommunityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
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
        System.out.println(searchText);
        try {
            // SpEL 표현식 파서 생성
            ExpressionParser parser = new SpelExpressionParser();
            // 표현식을 평가하여 결과를 반환
            Expression exp = parser.parseExpression(searchText);
            System.out.println(exp.getValue());
            model.addAttribute("searchText3", exp.getValue()); // 취약한 코드
        } catch (Exception e) {
            // 평가 중 에러 발생 시 에러 메시지 출력
            return "Error evaluating expression: " + e.getMessage();
        }
        model.addAttribute("searchText1", java.time.LocalDateTime.now()); // 취약한 코드
        model.addAttribute("searchText2", 7*7); // 취약한 코드


        return "templates/communitytest"; // Thymeleaf 템플릿을 반환
    }

}


