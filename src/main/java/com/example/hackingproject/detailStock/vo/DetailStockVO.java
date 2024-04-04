package com.example.hackingproject.detailStock.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailStockVO {
    private String price;
    private String unit;
    private String stock;
    private String userId;
    private int own;
    private int pivot = 0;
    private int AAPL=0;
    private int AMZN=0;
    private int FB=0;
    private int GOOGL=0;
    private int MSFT=0;
    private int cal;
    
    
    
    
    public void setStockQuantity() {
        if (stock.equals("AAPL")) {
            AAPL = Integer.parseInt(unit);
        }
        else if (stock.equals("AMZN")) {
            AMZN = Integer.parseInt(unit);
        }
        else if (stock.equals("FB")) {
            FB = Integer.parseInt(unit);
        }
        else if (stock.equals("GOOGL")) {
            GOOGL = Integer.parseInt(unit);
        } else {
            MSFT = Integer.parseInt(unit);
        }
    }
    
    public void setStockSellQuantity() {
    	int pv = Integer.parseInt(unit) * -1;
    	if (0 > own - pv) return;
    	if (stock.equals("AAPL")) {
            AAPL = pv;
        }
        else if (stock.equals("AMZN")) {
            AMZN = pv;
        }
        else if (stock.equals("FB")) {
            FB = pv;
        }
        else if (stock.equals("GOOGL")) {
            GOOGL = pv;
        } else {
            MSFT = pv;
        }
    }
}
