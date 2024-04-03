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
        
       if(Integer.parseInt(unit) < 0) {
    	   return ResponseEntity.badRequest().body(Map.of("MSG","0보다 큰 수를 기입 하십시오."));
       }
        
        // 비즈니스 로직 처리 후 결과 메시지 생성
        String message = user_id+"님의 구매 가격 : " + price + "\n구매 정보 : "+detailStockVO.getStock()+"\n구매 수량 : " + unit; 
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
    public ResponseEntity<?> haveStock(@RequestBody(required = false) DetailStockVO detailStockVO){
        // detailStockVO 객체가 null인 경우의 처리
        if (detailStockVO == null) {
            return ResponseEntity.badRequest().body("요청 본문이 비어 있습니다.");
        }
        
        // 받은 정보를 서비스로 넘긴다.
        int unit = 0;
        String stock = detailStockVO.getStock();
        String user_id = detailStockVO.getUserId();
        String message = user_id + "님 :" + stock; 
        System.out.println(message);

        detailStockVO = detailStockService.haveStock(detailStockVO);    

        if (stock != null) {
            switch (stock) {
                case "AAPL":
                    unit = detailStockVO.getAAPL();
                    break;
                case "AMZN":
                    unit = detailStockVO.getAMZN();
                    break;
                case "FB":
                    unit = detailStockVO.getFB();
                    break;
                case "GOOGL":
                    unit = detailStockVO.getGOOGL();
                    break;
                default:
                    unit = detailStockVO.getMSFT();
                    break;
            }
        }
        
        // user_id와 unit을 Map에 담아 반환
        return ResponseEntity.ok(Map.of("userId", user_id, "unit", unit));
    }

//    @PostMapping("/haveStock")
//    public ResponseEntity<?> haveStock(@RequestBody DetailStockVO detailStockVO){
//        
//        //받은 정보를 service 로 넘긴다.
//        int unit = 0;
//        String stock = detailStockVO.getStock();
//        String user_id = detailStockVO.getUserId();
//        String message = user_id + "님 :" + stock; 
//        System.out.println(message);
//        
//        detailStockVO = detailStockService.haveStock(detailStockVO);    
//        
//        if (stock != null) {
//            if (stock.equals("AAPL")) {
//                unit = detailStockVO.getAAPL();
//            } else if (stock.equals("AMZN")) {
//                unit = detailStockVO.getAMZN();
//            } else if (stock.equals("FB")) {
//                unit = detailStockVO.getFB();
//            } else if (stock.equals("GOOGL")) {
//                unit = detailStockVO.getGOOGL();
//            } else {
//                unit = detailStockVO.getMSFT();
//            }
//        }
//        
//        
//        // user_id와 unit을 Map에 담아 반
//        return ResponseEntity.ok(Map.of("userId", user_id, "unit", unit));
//    }
//    
}
