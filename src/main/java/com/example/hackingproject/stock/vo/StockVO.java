package com.example.hackingproject.stock.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StockVO {
    @JsonProperty("STOCK_CODE")
    public String STOCK_CODE;

    @JsonProperty("STOCK_NAME")
    public String STOCK_NAME;

    @JsonProperty("STOCK_PRICE")
    public Integer STOCK_PRICE;
}
