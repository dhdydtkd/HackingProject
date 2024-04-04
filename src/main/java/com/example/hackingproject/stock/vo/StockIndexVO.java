package com.example.hackingproject.stock.vo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockIndexVO {
    private String STOCK_INDEX_CODE;
    private String STOCK_INDEX_NUM;
    private String STOCK_INDEX_UPDOWN;
    private String STOCK_INDEX_CNG;
    private String STOCK_INDEX_CNG_PERCENT;
}
