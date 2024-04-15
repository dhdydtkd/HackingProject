package com.example.hackingproject.detailStock;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.text.NumberFormat;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.hackingproject.BaseModel;
import com.example.hackingproject.common.JwtTokenUtil;
import com.example.hackingproject.detailStock.service.DetailStockService;
import com.example.hackingproject.detailStock.vo.DetailStockVO;
import com.example.hackingproject.mypage.dto.MyUserData;

@Controller
public class DetailStockAPIController {

    @Value("${rsa_web_key}")
    private String RSA_WEB_KEY ; // 개인키 session key

    @Value("${rsa_instance}")
    private String RSA_INSTANCE; // rsa transformation

    @Autowired
    private DetailStockRESTAPIController de;

    @Autowired
    private DetailStockService detailStockService;

    public DetailStockAPIController(DetailStockService detailStockService) {
        this.detailStockService = detailStockService;
    }

    @PostMapping("/detailReload")
    public ResponseEntity<?> reload(HttpServletRequest request, @RequestBody DetailStockVO detailStockVO) {
        Map<String, Object> responseBody = new HashMap<>();
        MyUserData user = new MyUserData();
        String user_id = (String)request.getSession().getAttribute("user_id");
        String stockCode = detailStockVO.getStock();
        user.setUSER_ID(user_id);
        long unit = 0;
        detailStockVO.setUserId(user_id);
        detailStockVO = detailStockService.haveStock(detailStockVO);
        if (stockCode != null) {
            switch (stockCode) {
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
        user = detailStockService.getUserData(user);
        responseBody.put("haveStock", unit);
        responseBody.put("balance", user.getACCOUNT_BALANCE());
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/detailBuy")
    public ResponseEntity<?> buyStock(HttpServletRequest request, @RequestBody String E2EdetailStockData) {
        HttpSession session = request.getSession();
        PrivateKey privateKey = (PrivateKey) session.getAttribute(RSA_WEB_KEY);
        String detailStockJsonData = "";
        DetailStockVO detailStockVO = null;
        long total;
        try {
            detailStockJsonData = decryptRsa(privateKey, E2EdetailStockData);
        } catch (Exception e) {
            System.out.println("[주식 구매]복호화 에러");
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            detailStockVO = objectMapper.readValue(detailStockJsonData, DetailStockVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String price = String.valueOf(detailStockVO.getPrice());
        String unit = String.valueOf(detailStockVO.getUnit());
        String user_id = detailStockVO.getUserId();
        String isSuccess = detailStockService.buyStock(detailStockVO);
        if (isSuccess == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("MSG", "보유 금액 이상 구매 불가능");
            return ResponseEntity.ok(response);
        }
        total = Long.parseLong(isSuccess) * Long.parseLong(unit);
        String message = user_id + "님의 구매 가격 : " + NumberFormat.getNumberInstance().format(Long.parseLong(isSuccess)) + "\n구매 정보 : " + detailStockVO.getStock() + "\n구매 수량 : " + NumberFormat.getNumberInstance().format(Integer.parseInt(unit)) + "\n총 구매 가격 : " + NumberFormat.getNumberInstance().format(total);
        System.out.println(message);
        Map<String, Object> response = new HashMap<>();
        response.put("MSG", message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/detailSell")
    public ResponseEntity<?> sellStock(HttpServletRequest request, @RequestBody String E2EdetailStockData){
        HttpSession session = request.getSession();
        PrivateKey privateKey = (PrivateKey) session.getAttribute(RSA_WEB_KEY);
        String detailStockJsonData = "";
        DetailStockVO detailStockVO = null;
        try {
            detailStockJsonData = decryptRsa(privateKey, E2EdetailStockData);
        } catch (Exception e) {
            System.out.println("[주식 판매]복호화 에러");
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            detailStockVO = objectMapper.readValue(detailStockJsonData, DetailStockVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long cal_price;
        String price = String.valueOf(detailStockVO.getPrice());
        String unit = String.valueOf(detailStockVO.getUnit());
        String user_id = detailStockVO.getUserId();
        String message = "";
        String isSuccess = detailStockService.sellStock(detailStockVO);
        if(isSuccess != null) {
            cal_price = Long.parseLong(isSuccess) * Long.parseLong(unit);
            message = user_id+"님의 판매 가격 : " + NumberFormat.getNumberInstance().format(Integer.parseInt(isSuccess)) + "\n판매 수량 : " + NumberFormat.getNumberInstance().format(Integer.parseInt(unit)) + "\n총 판매 금액 : "+NumberFormat.getNumberInstance().format(cal_price) ;
            Map<String, Object> response = new HashMap<>();
            response.put("MSG", message);
            return ResponseEntity.ok(response);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("MSG", "보유 갯수 보다 많이 판매 하실 수 없습니다.");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/haveStock")
    public ResponseEntity<?> haveStock(@RequestBody(required = false) DetailStockVO detailStockVO){
        if (detailStockVO == null) {
            return ResponseEntity.badRequest().body("요청 본문이 비어 있습니다.");
        }
        int unit = 0;
        String stock = detailStockVO.getStock();
        String user_id = detailStockVO.getUserId();
        String message = user_id + "님 :" + stock;
        System.out.println(message);

        Map<String, Object> response = new HashMap<>();
        response.put("userId", user_id);
        return ResponseEntity.ok(response);
    }


    private String decryptRsa(PrivateKey privateKey, String securedValue) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_INSTANCE);
        byte[] encryptedBytes = hexToByteArray(securedValue);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedValue = new String(decryptedBytes, StandardCharsets.UTF_8);
        return decryptedValue;
    }

    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() % 2 != 0) {
            return new byte[] {};
        }

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[(int) Math.floor(i / 2)] = value;
        }
        return bytes;
    }
}
