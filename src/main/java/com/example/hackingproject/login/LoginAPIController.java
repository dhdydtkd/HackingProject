package com.example.hackingproject.login;

import com.example.hackingproject.BaseModel;
import com.example.hackingproject.login.dto.LoginReq;
import com.example.hackingproject.login.service.LoginService;
import com.example.hackingproject.login.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginAPIController {

    @Value("${rsa_web_key}")
    private String RSA_WEB_KEY ; // 개인키 session key

    @Value("${rsa_instance}")
    private String RSA_INSTANCE; // rsa transformation

    @Autowired
    private LoginService loginService;

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public BaseModel login(HttpServletRequest request, HttpServletResponse response
            , @RequestBody LoginReq loginReq) {
        BaseModel baseModel = new BaseModel();

        HttpSession session = request.getSession();
        PrivateKey privateKey = (PrivateKey) session.getAttribute(RSA_WEB_KEY);

        String userId = "";
        String userPw = "";
        try{
            userId = decryptRsa(privateKey, loginReq.getId());
            userPw = decryptRsa(privateKey, loginReq.getPass());
        }catch (Exception e){

        }
        // 복호화된 데이터
        LoginReq decryptionData = new LoginReq();
        decryptionData.setId(userId);
        decryptionData.setPass(userPw);

        loginReq.setId(userId);
        loginReq.setPass(SHA256Encrypt(userPw));

        UserVO userVO = loginService.getUserInfo(loginReq);
        //decryptionData

        //session.removeAttribute(RSA_WEB_KEY);
        Map<String, Object> result = new HashMap<String,Object>();
        result.put("userData",userVO);
        result.put("decryptionData",decryptionData);

        result.put("sha256pass",loginReq.getPass());
        baseModel.setBody(result);
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

    /**
     * SHA-256 암호화
     *
     * @param pass
     * @return String
     */
    public static String SHA256Encrypt(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes());
            byte[] byteData = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
