package com.example.hackingproject.detailStock;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.Map;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.hackingproject.stock.dto.StockData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${rsa_web_key}")
    private String RSA_WEB_KEY ; // 개인키 session key

    @Value("${rsa_instance}")
    private String RSA_INSTANCE; // rsa transformation

    @Autowired
	private DetailStockService detailStockService;

	public DetailStockAPIController(DetailStockService detailStockService) {
		this.detailStockService = detailStockService;
	}

    @PostMapping("/detailBuy")
    public ResponseEntity<?> buyStock(HttpServletRequest request,@RequestBody String E2EdetailStockData) {
        // payload에서 price와 unit을 추출
        HttpSession session = request.getSession();
        PrivateKey privateKey = (PrivateKey) session.getAttribute(RSA_WEB_KEY);
        String detailStockJsonData = "";
        DetailStockVO detailStockVO = null;
        try{
            detailStockJsonData = decryptRsa(privateKey, E2EdetailStockData);
        }catch (Exception e){
            System.out.println("[주식 구매]복호화 에러");
        }
        try {
            // JSON 데이터를 StockData 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            detailStockVO = objectMapper.readValue(detailStockJsonData, DetailStockVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String price = String.valueOf(detailStockVO.getPrice());
        String unit = String.valueOf(detailStockVO.getUnit());
        String user_id = detailStockVO.getUserId();



        // 비즈니스 로직 처리 후 결과 메시지 생성
        String message = user_id+"님의 구매 가격 : " + price + "\n구매 정보 : "+detailStockVO.getStock()+"\n구매 수량 : " + unit;
        System.out.println(message);

        boolean isSuccess = detailStockService.buyStock(detailStockVO);
        if(!isSuccess) {
        	return ResponseEntity.ok(Map.of("MSG","보유 금액 이상판매 불가능"));
        }
        // 클라이언트에게 JSON 형태로 응답 반환
        return ResponseEntity.ok(Map.of("MSG", message));
    }
    
    @PostMapping("/detailSell")
    public ResponseEntity<?> sellStock(HttpServletRequest request,@RequestBody String E2EdetailStockData){
        HttpSession session = request.getSession();
        PrivateKey privateKey = (PrivateKey) session.getAttribute(RSA_WEB_KEY);
        String detailStockJsonData = "";
        DetailStockVO detailStockVO = null;
        try{
            detailStockJsonData = decryptRsa(privateKey, E2EdetailStockData);
        }catch (Exception e){
            System.out.println("[주식 판매]복호화 에러");
        }
        try {
            // JSON 데이터를 StockData 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            detailStockVO = objectMapper.readValue(detailStockJsonData, DetailStockVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String price = String.valueOf(detailStockVO.getPrice());
        String unit = String.valueOf(detailStockVO.getUnit());
        String user_id = detailStockVO.getUserId();
    	
        String message = user_id+"님의 판매 가격 : " + price + ", 판매 수량 : " + unit; 

        detailStockService.sellStock(detailStockVO);      
    	
    	return ResponseEntity.ok(Map.of("MSG", message));
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


        return ResponseEntity.ok(Map.of("userId", user_id));
    }

    /**
     * 복호화
     *
     * @param privateKey
     * @param securedValue
     * @return
     * @throws Exception
     */
    private String decryptRsa(PrivateKey privateKey, String securedValue) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_INSTANCE);
        byte[] encryptedBytes = hexToByteArray(securedValue);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedValue = new String(decryptedBytes, StandardCharsets.UTF_8); // 문자 인코딩 주의.
        return decryptedValue;
    }
    /**
     * 16진 문자열을 byte 배열로 변환한다.
     *
     * @param hex
     * @return
     */
    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() % 2 != 0) { return new byte[] {}; }

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[(int) Math.floor(i / 2)] = value;
        }
        return bytes;
    }
}
