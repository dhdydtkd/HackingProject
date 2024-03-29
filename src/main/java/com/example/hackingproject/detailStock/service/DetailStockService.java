package com.example.hackingproject.detailStock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hackingproject.dao.DetailStockDAO;
import com.example.hackingproject.detailStock.vo.DetailStockVO;

@Service("DetailStockService")
public class DetailStockService {
	private DetailStockDAO detailStockDAO;
	
    @Autowired
    public DetailStockService(DetailStockDAO detailStocDAO) {
        this.detailStockDAO = detailStocDAO;
    }
	
    public void buyStock(DetailStockVO detailStockVO) {
        // UserEntity 객체의 데이터를 로그로 출력
    	detailStockVO.setStockQuantity();
    	String message = detailStockVO.getUserId()+"님 service Level =>가격 : " + detailStockVO.getPrice() + ", 구매 수량 : " + detailStockVO.getUnit() + "주식 : "+detailStockVO.getStock()+""; 
    	System.out.println(message);
        detailStockDAO.buyStock(detailStockVO);
    }
    
    public void sellStock(DetailStockVO detailStockVO) {
    	detailStockVO.setStockQuantity();
    	String message = detailStockVO.getUserId()+"님 service Level =>가격 : " + detailStockVO.getPrice() + ", 판매 수량 : " + detailStockVO.getUnit() + "주식 : "+detailStockVO.getStock()+""; 
    	System.out.println(message);
        detailStockDAO.sellStock(detailStockVO);
    }
    
    public DetailStockVO haveStock(DetailStockVO detailStockVO) {
    	
    	detailStockVO = detailStockDAO.haveStock(detailStockVO);
    	//detailStockVO.setStockQuantity();
    	String user_id = detailStockVO.getUserId();
    	int S = detailStockVO.getSAMSUNG();
    	int L = detailStockVO.getLG();
    	int T = detailStockVO.getTESLA();
    	int A = detailStockVO.getAPPLE();
    	int SK = detailStockVO.getSK();
    	
    	System.out.println(user_id+"님의 보유 주식 개수 \nSAMSUNG: "+S+"\nLG: "+L+"\nTESLA: "+T+"\nAPPLE: "+A+"\nSK: "+SK);
    	return detailStockVO;
    }
	
}
