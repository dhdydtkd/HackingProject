package com.example.hackingproject.detailStock;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.hackingproject.detailStock.service.DetailStockService;
import com.example.hackingproject.detailStock.vo.DetailStockVO;

@Controller
public class DetailStockAPIController {
	private DetailStockService detailStockService;
	public DetailStockAPIController(DetailStockService detailStockService) {
		this.detailStockService = detailStockService;
	}
    @PostMapping("/detailBuy")
    public ResponseEntity<?> buyStock(@RequestBody DetailStockVO detailStockVO) {
        // payload에서 price와 unit을 추출
        String price = String.valueOf(detailStockVO.getPrice());
        String unit = String.valueOf(detailStockVO.getUnit());
        String user_id = detailStockVO.getUserId();
        
        // 비즈니스 로직 처리 후 결과 메시지 생성
        String message = user_id+"님의 구매 가격 : " + price + ", 구매 수량 : " + unit; 
        System.out.println(message);
        
        detailStockService.buyStock(detailStockVO);
        
        // 클라이언트에게 JSON 형태로 응답 반환
        return ResponseEntity.ok(Map.of("MSG", message));
    }
    
    @PostMapping("/detailSell")
    public ResponseEntity<?> sellStock(@RequestBody DetailStockVO detailStockVO){
    	
        String price = String.valueOf(detailStockVO.getPrice());
        String unit = String.valueOf(detailStockVO.getUnit());
        String user_id = detailStockVO.getUserId();
    	
        String message = user_id+"님의 판매 가격 : " + price + ", 판매 수량 : " + unit; 
        
        detailStockService.sellStock(detailStockVO);      
    	
    	return ResponseEntity.ok(Map.of("MSG", " "));
    }
    
    @PostMapping("/haveStock")
    public ResponseEntity<?> haveStock(@RequestBody DetailStockVO detailStockVO){
        
        //받은 정보를 service 로 넘긴다.
        int unit = 0;
        String stock = detailStockVO.getStock();
        String user_id = detailStockVO.getUserId();
        String message = user_id + "님 :" + stock; 
        //System.out.println(message);
        
        detailStockVO = detailStockService.haveStock(detailStockVO);    
        
        if (stock != null) {
            if (stock.equals("SAMSUNG")) {
                unit = detailStockVO.getSAMSUNG();
            } else if (stock.equals("TESLA")) {
                unit = detailStockVO.getTESLA();
            } else if (stock.equals("LG")) {
                unit = detailStockVO.getLG();
            } else if (stock.equals("SK")) {
                unit = detailStockVO.getSK();
            } else {
                unit = detailStockVO.getAPPLE();
            }
        }
        
        
        // user_id와 unit을 Map에 담아 반환
        return ResponseEntity.ok(Map.of("userId", user_id, "unit", unit));
    }
    
}
