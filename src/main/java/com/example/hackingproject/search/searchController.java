package com.example.hackingproject.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.hackingproject.stock.vo.StockVO;
import com.example.hackingproject.search.service.searchService;
import com.example.hackingproject.search.vo.searchVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class searchController {
	@Autowired
	private searchService search;

	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody searchVO key) {
		List<StockVO> result = search.search(key);
		if (!result.isEmpty()) {
			// StockVO 리스트를 JSON 형식으로 변환
			ObjectMapper objectMapper = new ObjectMapper();
			String stockListJson = null;
			try {
				// StockVO 리스트를 JSON 문자열로 변환
				stockListJson = objectMapper.writeValueAsString(result);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			// 직접 HashMap 생성하여 반환
			Map<String, Object> responseMap = new HashMap<>();
			responseMap.put("MSG", true);
			responseMap.put("stockList", result);
			return ResponseEntity.ok(responseMap);
		} else {
			// 직접 HashMap 생성하여 반환
			Map<String, Object> responseMap = new HashMap<>();
			responseMap.put("MSG", false);
			return ResponseEntity.ok(responseMap);
		}
	}
}
