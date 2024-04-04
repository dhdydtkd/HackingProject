package com.example.hackingproject.mypage;

import com.example.hackingproject.BaseModel;
import com.example.hackingproject.common.JwtTokenUtil;
import com.example.hackingproject.detailStock.vo.DetailStockVO;
import com.example.hackingproject.login.dto.LoginReq;
import com.example.hackingproject.mypage.dto.MyUserData;
import com.example.hackingproject.mypage.dto.SendData;
import com.example.hackingproject.mypage.service.MyPageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;

@RestController
@RequestMapping("/mypage")
public class MyPageAPIController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MyPageService myPageService;

    @Value("${rsa_web_key}")
    private String RSA_WEB_KEY ; // 개인키 session key

    @Value("${rsa_instance}")
    private String RSA_INSTANCE; // rsa transformation

    @RequestMapping(method = RequestMethod.POST, path = "/userinfo")
    public BaseModel login(HttpServletRequest request, HttpServletResponse response) {
        BaseModel baseModel = new BaseModel();
        String JWTToken = jwtTokenUtil.GetJWTCookie(request);
        String user_id = jwtTokenUtil.getUserIdFromToken(JWTToken);
        MyUserData myUserData = myPageService.getUserInfo(user_id);
        baseModel.setBody(myUserData);
        return baseModel;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/send")
    public BaseModel send(HttpServletRequest request, HttpServletResponse response
            , @RequestBody String E2ESendData) {
        BaseModel baseModel = new BaseModel();
        String JWTToken = jwtTokenUtil.GetJWTCookie(request);
        String user_id = jwtTokenUtil.getUserIdFromToken(JWTToken);
        // E2E 암호화

        HttpSession session = request.getSession();
        PrivateKey privateKey = (PrivateKey) session.getAttribute(RSA_WEB_KEY);
        String sendJson = "";
        SendData sendJsonData = null;
        try{
            sendJson = decryptRsa(privateKey, E2ESendData);
        }catch (Exception e){
            System.out.println("[송금]복호화 에러");
            System.out.println(e.getMessage());
        }
        try {
            // JSON 데이터를 StockData 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            sendJsonData = objectMapper.readValue(sendJson, SendData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 금액 확인
        // 상대 계좌 존재 확인

        baseModel.setBody(sendJsonData);
        return baseModel;
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
