package com.example.hackingproject.detailStock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hackingproject.dao.DetailStockDAO;
import com.example.hackingproject.detailStock.vo.DetailStockVO;
import com.example.hackingproject.mypage.dto.MyUserData;

@Service("DetailStockService")
public class DetailStockService {
	private DetailStockDAO detailStockDAO;
	
    @Autowired
    public DetailStockService(DetailStockDAO detailStocDAO) {
        this.detailStockDAO = detailStocDAO;
    }
	
    public String buyStock(DetailStockVO detailStockVO) {
        // UserEntity 객체의 데이터를 로그로 출력
    	detailStockVO.setStockQuantity();
    	String message = detailStockVO.getUserId()+"님 service Level =>가격 : " + detailStockVO.getPrice() + ", 구매 수량 : " + detailStockVO.getUnit() + "주식 : "+detailStockVO.getStock()+""; 
    	String price = null;
    	price = String.valueOf(detailStockDAO.getStockPrice(detailStockVO.getStock()));

    	int cal = Integer.parseInt(price)* Integer.parseInt(detailStockVO.getUnit());
    	
    	System.out.println(message+"합 : "+ cal);
    	
    	detailStockVO.setPrice(price);
    	detailStockVO.setCal(cal);
    	
    	int balance = detailStockDAO.getUserData(detailStockVO.getUserId()).getACCOUNT_BALANCE();
    	System.out.println("최신 가격 : "+ price+"bal : "+balance);
    	if(balance > 0  && balance - cal > 0) {
    		detailStockDAO.minusAccount(detailStockVO);
    		detailStockDAO.buyStock(detailStockVO);
            detailStockDAO.insertUser(detailStockVO);
            return price;
    	}else return price;
    	
    	
    }
    
    public String sellStock(DetailStockVO detailStockVO) {
    	detailStockVO.setStockSellQuantity();
    	String message = detailStockVO.getUserId()+"님 service Level =>가격 : " + detailStockVO.getPrice() + ", 판매 수량 : " + detailStockVO.getUnit() + "주식 : "+detailStockVO.getStock()+""+detailStockVO.getAAPL(); 
    	int unit = 0;
    	int getUnit = Integer.parseInt(detailStockVO.getUnit());
    	String price = null;
    	
    	detailStockVO.setUnit("-"+detailStockVO.getUnit());
    	DetailStockVO pivot = new DetailStockVO();
    	pivot.setUserId(detailStockVO.getUserId());
    	pivot = this.haveStock(pivot);
    	
    	String stockCode = detailStockVO.getStock();
    	price = String.valueOf(detailStockDAO.getStockPrice(stockCode));
    	System.out.println("Service Level  server price- - >"+ price+"code"+stockCode);
    	if (stockCode != null) {
            switch (stockCode) {
                case "AAPL":
                    unit = pivot.getAAPL();
                    break;
                case "AMZN":
                    unit = pivot.getAMZN();
                    break;
                case "FB":
                    unit = pivot.getFB();
                    break;
                case "GOOGL":
                    unit = pivot.getGOOGL();
                    break;
                default:
                    unit = pivot.getMSFT();
                    break;
            }
        }
    	int cal = Integer.parseInt(price)* getUnit;
    	detailStockVO.setCal(cal);
    	if(unit >=  getUnit) {
	        detailStockDAO.sellStock(detailStockVO);
	        detailStockDAO.insertUser(detailStockVO);
	        detailStockDAO.plusAccount(detailStockVO);
	        return price;
	    }return price;
    }
    
    public DetailStockVO haveStock(DetailStockVO detailStockVO) {
    	String user_id = detailStockVO.getUserId();
    	
    	detailStockVO = detailStockDAO.haveStock(detailStockVO);
    	//detailStockVO.setStockQuantity();
    	int S = detailStockVO.getAAPL();
    	int L = detailStockVO.getAMZN();
    	int T = detailStockVO.getFB();
    	int A = detailStockVO.getGOOGL();
    	int SK = detailStockVO.getMSFT();
    	System.out.println(user_id+"님의 보유 주식 개수 \nAAPL: "+S+"\nAMZN: "+L+"\nFB: "+T+"\nGOOGL: "+A+"\nMSFT: "+SK);
    	
    	
    	
    	
    	return detailStockVO;
    }
    
    public MyUserData getUserData(MyUserData user) {
    	String user_id = user.getUSER_ID();
    	user = detailStockDAO.getUserData(user_id);
    	
    	
    	return user;
    }
	
}
