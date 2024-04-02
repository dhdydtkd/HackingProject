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
        detailStockDAO.insertUser(detailStockVO);
    }
    
    public void sellStock(DetailStockVO detailStockVO) {
    	detailStockVO.setStockSellQuantity();
    	String message = detailStockVO.getUserId()+"님 service Level =>가격 : " + detailStockVO.getPrice() + ", 판매 수량 : " + detailStockVO.getUnit() + "주식 : "+detailStockVO.getStock()+""+detailStockVO.getAAPL(); 
    	System.out.println(message);
    	//음수로 바꾸
    	detailStockVO.setUnit("-"+detailStockVO.getUnit());
    	DetailStockVO pivot = this.haveStock(detailStockVO);

        detailStockDAO.sellStock(detailStockVO);
        detailStockDAO.insertUser(detailStockVO);
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
	
}
