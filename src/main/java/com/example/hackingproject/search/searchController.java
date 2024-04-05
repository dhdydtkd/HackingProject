package com.example.hackingproject.search;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.hackingproject.search.service.searchService;
import com.example.hackingproject.search.vo.searchVO;



@Controller
public class searchController {
    @Autowired
	private searchService search;
    
	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody searchVO key) {

		System.out.println("keywords : "+key.getKeywords());
		boolean isTrue = search.search(key);
		return ResponseEntity.ok(Map.of("MSG", isTrue));
	}
}
