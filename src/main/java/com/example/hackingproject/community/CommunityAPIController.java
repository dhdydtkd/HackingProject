package com.example.hackingproject.community;

import com.example.hackingproject.BaseModel;
import com.example.hackingproject.community.service.CommunityService;
import com.example.hackingproject.community.vo.CommunityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class CommunityAPIController {

    @Autowired
    CommunityService communityService;

    @PostMapping("/community/search")
    public ResponseEntity<?> communitySearch(@RequestBody String searchText) {

        List<CommunityVO> communityList = null;

        if(searchText.equals("search_text")){
            searchText="";
        }
        communityList = communityService.communitySearch(searchText);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("data", communityList);
        responseData.put("searchText", searchText); // 추가 데이터
        return ResponseEntity.ok(responseData);
    }
}
