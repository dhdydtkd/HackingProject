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
    private long own;
    private long pivot = 0;
    private long AAPL=0;
    private long AMZN=0;
    private long FB=0;
    private long GOOGL=0;
    private long MSFT=0;
    private long cal;
    
    
    
    
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
