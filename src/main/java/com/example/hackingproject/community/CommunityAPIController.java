package com.example.hackingproject.community;

import com.example.hackingproject.community.service.CommunityService;
import com.example.hackingproject.community.vo.CommunityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class CommunityAPIController {

    @Autowired
    CommunityService communityService;

    @PostMapping("/community/search")
    public ResponseEntity<?> communitySearch(@RequestBody String searchText) {


        Map<String, Object> responseData = new HashMap<>();
        try {
            // SpEL 표현식 파서 생성
            ExpressionParser parser = new SpelExpressionParser();
            // 표현식을 평가하여 결과를 반환
            Expression exp = parser.parseExpression(searchText);
            System.out.println(exp.getValue().toString());
            responseData.put("searchText", exp.getValue().toString()); // 취약한 코드
        } catch (Exception e) {
        }

//        responseData.put("searchText", searchText); // 추가 데이터
        return ResponseEntity.ok(responseData);
    }
}
