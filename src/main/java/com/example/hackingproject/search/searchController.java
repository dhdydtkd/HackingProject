package com.example.hackingproject.search;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.sql.ResultSet;

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

import java.util.List;
import java.util.ArrayList;



@Controller
public class searchController {
    @Autowired
	private searchService search;

	@PostMapping("/search")
	public ResponseEntity<?> search(@RequestBody searchVO key) {
		List<StockVO> result = null;

		result = search.search(key);
		if(!result.isEmpty()){

			// StockVO 리스트를 JSON 형식으로 변환
			ObjectMapper objectMapper = new ObjectMapper();
			String stockListJson = null;
			try {
				// StockVO 리스트를 JSON 문자열로 변환
				stockListJson = objectMapper.writeValueAsString(result);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			// JSON 문자열을 Map.of 메서드에 전달하지 않고, 직접 객체로 변환하여 전달
			return ResponseEntity.ok(Map.of("MSG", true, "stockList", result));
		}

		else{
			return ResponseEntity.ok(Map.of("MSG", false));
		}

	}

}
