package com.example.hackingproject.stock.service;

import com.example.hackingproject.common.vo.TableSearchVO;
import com.example.hackingproject.config.retrofit.RestInterface;
import com.example.hackingproject.config.retrofit.RetrofitAPI;
import com.example.hackingproject.dao.NoticeDAO;
import com.example.hackingproject.dao.StockDAO;
import com.example.hackingproject.notice.vo.NoticeVO;
import com.example.hackingproject.stock.vo.StockIndexVO;
import com.example.hackingproject.config.jsoup.JsoupAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;




import java.util.ArrayList;
import java.util.List;

@Service("StockIndexService")
public class StockIndexService {
    JsoupAPI jsoup;

    public List<StockIndexVO> getStockIndex(){
        jsoup = new JsoupAPI();
        List<StockIndexVO> index_list = new ArrayList<>();


        StockIndexVO kospi =  new StockIndexVO();
        StockIndexVO kosdaq =  new StockIndexVO();
        StockIndexVO nasdaq =  new StockIndexVO();
        StockIndexVO snp500 =  new StockIndexVO();
        StockIndexVO exchange_rate =  new StockIndexVO();

        kospi = jsoup.getKospiData("KOSPI");
        kosdaq = jsoup.getKospiData("KOSDAQ");
        nasdaq = jsoup.getWorldData("NASDAQ");
        snp500 = jsoup.getWorldData("S&P500");
        exchange_rate = jsoup.getExchangeRate();

        index_list.add(kospi);
        index_list.add(kosdaq);
        index_list.add(nasdaq);
        index_list.add(snp500);
        index_list.add(exchange_rate);



        return index_list;

    }
}
