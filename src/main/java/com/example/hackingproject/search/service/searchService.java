package com.example.hackingproject.search.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Service;

import com.example.hackingproject.search.vo.searchVO;

@Service
public class searchService {

	public boolean search(searchVO key) {
        String url = "jdbc:mysql://rds-17-hack.cggbeg5i6y5t.eu-north-1.rds.amazonaws.com/rookie"; // 데이터베이스 URL
        String username = "root"; // 사용자 이름
        String password = "k44g20!!"; // 비밀번호
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM notice WHERE title LIKE '%"+key.getKeywords()+"%'")) {
        	
               while (rs.next()) {
            	   System.out.println("true");
                   return true;
               }System.out.println("false");
               return false;
           } catch (SQLException e) {
               e.printStackTrace();
               return false;
           }
	}
}
