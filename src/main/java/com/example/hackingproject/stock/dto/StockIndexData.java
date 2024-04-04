package com.example.hackingproject.stock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockIndexData {

    private String index_code;
    private String index_num;
    private String index_updown;
    private String index_change;
    private String index_change_percent;
    private String index_date;
}
