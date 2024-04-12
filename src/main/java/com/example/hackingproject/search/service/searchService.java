package com.example.hackingproject.search.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Service;

import com.example.hackingproject.search.vo.searchVO;
import com.example.hackingproject.stock.vo.StockVO;

import javax.xml.transform.Result;
import java.util.List;
import java.util.ArrayList;



@Service
public class searchService {

	public List<StockVO> search(searchVO key) {
        List<StockVO> stockList = new ArrayList<>();
        String url = "jdbc:mysql://rds-17-hack.cggbeg5i6y5t.eu-north-1.rds.amazonaws.com/rookie"; // 데이터베이스 URL
        String username = "root"; // 사용자 이름
        String password = "k44g20!!"; // 비밀번호


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
