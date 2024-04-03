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
    private int SAMSUNG=0;
    private int TESLA=0;
    private int LG=0;
    private int SK=0;
    private int APPLE=0;
    
    
    
    
    public void setStockQuantity() {
        if (stock.equals("SAMSUNG")) {
            SAMSUNG = Integer.parseInt(unit);
        }
        else if (stock.equals("TESLA")) {
            TESLA = Integer.parseInt(unit);
        }
        else if (stock.equals("LG")) {
            LG = Integer.parseInt(unit);
        }
        else if (stock.equals("SK")) {
            SK = Integer.parseInt(unit);
        } else {
            APPLE = Integer.parseInt(unit);
        }
    }
}
